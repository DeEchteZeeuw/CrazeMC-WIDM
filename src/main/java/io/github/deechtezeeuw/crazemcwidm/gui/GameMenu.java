package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class GameMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Inventory gui = Bukkit.getServer().createInventory(
                player,
                this.size(),
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i < this.size();i++) {
            gui.setItem(i, this.background());
        }

        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        // Map details
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5"+plugin.getConfigManager().getMain().getConfig().getString("mappen."+game.getTheme()+".title")));
        gui.setItem(10, this.menuItem("&d&lMap &8>>", "MAP", 1, 0, lore));

        // Hosts details
        lore = new ArrayList<>();
        for (UUID singleHost : game.getHosts()) {
            if (Bukkit.getServer().getPlayer(singleHost) != null) {
                lore.add(ChatColor.translateAlternateColorCodes('&', " &8>> &2"+Bukkit.getServer().getPlayer(singleHost).getName()));
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', " &8>> &4"+Bukkit.getOfflinePlayer(singleHost).getName()));
            }
        }
        gui.setItem(11, this.menuItem("&d&lHost(s) &8>>", "WORKBENCH", 1, 0, lore));

        // Game status details
        if (game.getGameStatus() != null) {
            switch (game.getGameStatus()) {
                case 0:
                    lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &e&lVullende"));
                    gui.setItem(12, this.menuItem("&d&lStatus &8>>", "WOOL", 1, 4, lore));
                    break;
                case 1:
                    lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &5Spelende"));
                    gui.setItem(12, this.menuItem("&d&lStatus &8>>", "WOOL", 1, 10, lore));
                    break;
                case 2:
                    lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &9Gepauzeerd"));
                    gui.setItem(12, this.menuItem("&d&lStatus &8>>", "WOOL", 1, 9, lore));
                    break;
                case 3:
                    lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &4Afgelopen"));
                    gui.setItem(12, this.menuItem("&d&lStatus &8>>", "WOOL", 1, 14, lore));
                    break;
            }
        } else {
            lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &4Fout opgetreden"));
            gui.setItem(12, this.menuItem("&d&lStatus &8>>", "WOOL", 1, 14, lore));
        }

        // Players / color
        lore = new ArrayList<>();
        for (Contestant contestant : game.getContestant()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> " + contestant.getChatColor() + "(" + contestant.getColorName() + ")" + contestant.getPlayername() ));
        }
        gui.setItem(13, this.menuItem("&d&lPlayers &8>>", "MAGENTA_SHULKER_BOX", 1, 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&dJouw game info";
    }

    @Override
    public Integer size() {
        return 27;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().hostBackgroundMaterial), plugin.getConfigManager().getGui().hostBackgroundAmount, plugin.getConfigManager().getGui().hostBackgroundShort);
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
