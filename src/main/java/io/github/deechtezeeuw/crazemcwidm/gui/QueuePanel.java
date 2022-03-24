package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class QueuePanel extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void openForColor(Contestant contestant, Player host) {
        if (contestant == null || host == null) {
            new ColorPanel().openColor(contestant, host);
            return;
        }
        int size = 9;
        ArrayList<UUID> queueList = plugin.getGameManager().getQueue();
        if (queueList.size() > 9) size = 18;
        if (queueList.size() > 18) size = 27;
        if (queueList.size() > 27) size = 36;
        if (queueList.size() > 36) size = 45;
        if (queueList.size() > 45) size = 54;

        Inventory gui = Bukkit.getServer().createInventory(
                host,
                size,
                ChatColor.translateAlternateColorCodes('&', this.title() + contestant.getChatColor() + "&lQueue panel")
        );

        // Set background
        for (int i = 0; i < this.size();i++) {
            gui.setItem(i, this.background());
        }

        if (plugin.getGameManager().getQueue().size() == 0) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Kleur: " + contestant.getChatColor() + "&l" + contestant.getColorName()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&8>> &7Klik om terug te gaan"));
            gui.setItem(4, this.menuItem("&c&lGeen spelers in de queue &8>>", "BARRIER", 1, 0, lore));
        } else {
            int place = 0;
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Kleur: " + contestant.getChatColor() + "&l" + contestant.getColorName()));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik om hem/haar toe te voegen"));
            for (UUID uuid : queueList) {
                gui.setItem(place, this.headItem(Bukkit.getServer().getPlayer(uuid), null, lore));
                place++;
            }
        }

        host.openInventory(gui);
    }

    @Override
    public void open(Player player) {
        player.closeInventory();
        return;
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider;
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
