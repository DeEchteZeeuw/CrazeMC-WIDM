package io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.weaponsSubs;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.GraphicalUserInterface;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class OthersMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
            return;
        }

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                this.size(),
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i< this.size();i++) {
            gui.setItem(i, this.background());
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om dit item te krijgen"));

        // Red dye
        gui.setItem(10, this.menuItem("&4&lRed dye &8>>", "INK_SACK", 1, 1, lore));
        // Orange dye
        gui.setItem(11, this.menuItem("&6&lOrange dye &8>>", "INK_SACK", 1, 14, lore));
        // Yellow dye
        gui.setItem(12, this.menuItem("&e&lYellow dye &8>>", "INK_SACK", 1, 11, lore));
        // Green dye
        gui.setItem(13, this.menuItem("&2&lGreen dye &8>>", "INK_SACK", 1, 2, lore));
        // Lime dye
        gui.setItem(14, this.menuItem("&a&lLime dye &8>>", "INK_SACK", 1, 10, lore));
        // Blue dye
        gui.setItem(15, this.menuItem("&1&lBlue dye &8>>", "INK_SACK", 1, 4, lore));
        // Cyan dye
        gui.setItem(16, this.menuItem("&3&lCyan dye &8>>", "INK_SACK", 1, 6, lore));
        // Lightblue dye
        gui.setItem(19, this.menuItem("&b&lLightblue dye &8>>", "INK_SACK", 1, 12, lore));
        // Purple dye
        gui.setItem(20, this.menuItem("&5&lPurple dye &8>>", "INK_SACK", 1, 5, lore));
        // Magenta dye
        gui.setItem(21, this.menuItem("&d&lMagenta dye &8>>", "INK_SACK", 1, 13, lore));
        // Pink dye
        gui.setItem(22, this.menuItem("&c&lPink dye &8>>", "INK_SACK", 1, 9, lore));
        // Gray dye
        gui.setItem(23, this.menuItem("&8&lGray dye &8>>", "INK_SACK", 1, 8, lore));
        // Lightgray dye
        gui.setItem(24, this.menuItem("&7&lLightgray dye &8>>", "INK_SACK", 1, 7, lore));
        // White dye
        gui.setItem(25, this.menuItem("&f&lWhite dye &8>>", "INK_SACK", 1, 15, lore));
        // Stick
        gui.setItem(30, this.menuItem("&6&lStick &8>>", "STICK", 1, 0, lore));
        // Black dye
        gui.setItem(31, this.menuItem("&0&lBlack dye &8>>", "INK_SACK", 1, 0, lore));
        // Knockback Stick
        gui.setItem(32, this.menuItem("&6&lKnockback Stick &8>>", "STICK", 1, 0, lore));
        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het weapons menu"));
        gui.setItem(40, this.menuItem("&c&lTerug naar weapons &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().othersTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().othersSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().othersBackgroundMaterial), plugin.getConfigManager().getGui().othersBackgroundAmount, plugin.getConfigManager().getGui().othersBackgroundShort);
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
        if (!material.equalsIgnoreCase("barrier")) {
            MetaItem.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            if (title.toLowerCase().contains("knockback")) {
                MetaItem.addEnchant(Enchantment.KNOCKBACK, 2, true);
            }
        }
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }
}
