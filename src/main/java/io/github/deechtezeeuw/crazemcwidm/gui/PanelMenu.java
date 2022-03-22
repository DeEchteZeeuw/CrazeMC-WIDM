package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PanelMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                (game.getContestant().size() > 7) ? this.size() : this.size()-9,
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i < (game.getContestant().size() > 7 ? this.size() : this.size()-9);i++) {
            gui.setItem(i, this.background());
        }

        ArrayList<String> lore = new ArrayList<>();
        for (int i = 0; i < game.getContestant().size();i++) {
            if (28+i==35 || 28+i==36) continue;
            Contestant contestant = game.getContestant().get(i);
            lore = new ArrayList<>();
            // Contestant playername
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpeler: " + (contestant.getPlayername().equalsIgnoreCase("Leeg") ? "&c&lLeeg" : contestant.getPlayername() )));
            // Contestant role
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lRol: &b&l" + contestant.getRoleName()));
            // Contestant kills
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lKill(s): " + contestant.getKills()));
            // Contestant death
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lDood: " + (contestant.getDeath() ? "&2&l✔" : "&4&l✘")));
            // Contestant peacekeeper
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lPeacekeeper: " + (contestant.getPeacekeeper() ? "&2&l✔" : "&4&l✘")));
            // Contestant peacekeeper kills
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lPKKill(s): " + contestant.getPeacekeeperKills()));
            // Contestant spawn
            if (contestant.getSpawn() != null) {
                Location location = contestant.getSpawn();
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ()));
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: &c&lNiet geset"));
            }
            // Click on me to open color menu
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7&lKlik om " + contestant.getChatColor() + "&l" + contestant.getColorName() + " &7&laan te passen"));
            gui.setItem(28+i, this.menuItem(contestant.getChatColor()+"&l"+contestant.getColorName()+" &8>>", contestant.getShulkerMaterial().toString(), 1, 0, lore));
        }

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().panelTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().panelSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().panelBackgroundMaterial), plugin.getConfigManager().getGui().panelBackgroundAmount, plugin.getConfigManager().getGui().panelBackgroundShort);
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
}
