package io.github.deechtezeeuw.crazemcwidm.gui.books;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.GraphicalUserInterface;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Teleport extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        int size = 27;

        // check if user is in a game
        if (!plugin.getSQL().sqlSelect.playerIsInAGame(player.getUniqueId())) {
            player.closeInventory();
            return;
        }

        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());

        ArrayList<Contestant> alivePlayers = new ArrayList<>();
        for (Contestant contestant : game.getContestant()) {
            if (contestant.getPlayer() != null) {
                if (!contestant.getDeath()) {
                    if (plugin.getGameManager().getTeleportChoice().containsKey(player.getUniqueId())) {
                        if (!plugin.getGameManager().getTeleportChoice().get(player.getUniqueId()).equals(contestant.getPlayer())) {
                            alivePlayers.add(contestant);
                        }
                    } else {
                        alivePlayers.add(contestant);
                    }
                }
            }
        }

        if (alivePlayers.size() > 7) size = 36;

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                size,
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i < size;i++) {
            gui.setItem(i, this.background());
        }

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik op deze speler om hem/haar te teleporteren naar je volgende keuze"));
        int place = 10;
        for (Contestant contestant : alivePlayers) {
            if (place == 17) place = place+2;
            if (Bukkit.getServer().getPlayer(contestant.getPlayer()) != null) {
                gui.setItem(place, this.headItem(Bukkit.getServer().getPlayer(contestant.getPlayer()), null, lore));
            } else {
                gui.setItem(place, this.headItem(null, Bukkit.getServer().getOfflinePlayer(contestant.getPlayer()), lore));
            }
            place++;
        }

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&b&lTeleport";
    }

    @Override
    public Integer size() {
        return 9;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 15);
        ItemMeta MetaBackgroundItem = BackgroundItem.getItemMeta();
        MetaBackgroundItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        BackgroundItem.setItemMeta(MetaBackgroundItem);
        MetaBackgroundItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', " "));
        BackgroundItem.setItemMeta(MetaBackgroundItem);
        return BackgroundItem;
    }

    @Override
    public ItemStack menuItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack headItem(Player player, OfflinePlayer offlinePlayer, ArrayList<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (player != null) {
            skullMeta.setOwner(player.getName());
        } else {
            skullMeta.setOwningPlayer(offlinePlayer);
        }
        skull.setItemMeta(skullMeta);
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l"+(player == null ? offlinePlayer.getName() : player.getName())+" &8>>"));
        itemMeta.setLore(lore);
        skull.setItemMeta(itemMeta);
        return skull;
    }
}
