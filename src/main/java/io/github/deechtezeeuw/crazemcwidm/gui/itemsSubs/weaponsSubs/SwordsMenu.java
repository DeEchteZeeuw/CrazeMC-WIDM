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

public class SwordsMenu extends GraphicalUserInterface {
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
        // Diamond sword
        gui.setItem(11, this.swordItem("&d&lDiamond sword", "DIAMOND_SWORD", 1,0, lore, 0));
        gui.setItem(20, this.swordItem("&d&lDiamond sword", "DIAMOND_SWORD", 1,0, lore, 1));
        gui.setItem(29, this.swordItem("&d&lDiamond sword", "DIAMOND_SWORD", 1,0, lore, 2));
        gui.setItem(38, this.swordItem("&d&lDiamond sword", "DIAMOND_SWORD", 1,0, lore, 3));
        // Gold sword
        gui.setItem(12, this.swordItem("&d&lGold sword", "GOLD_SWORD", 1,0, lore, 0));
        gui.setItem(21, this.swordItem("&d&lGold sword", "GOLD_SWORD", 1,0, lore, 1));
        gui.setItem(30, this.swordItem("&d&lGold sword", "GOLD_SWORD", 1,0, lore, 2));
        gui.setItem(39, this.swordItem("&d&lGold sword", "GOLD_SWORD", 1,0, lore, 3));
        // Iron sword
        gui.setItem(13, this.swordItem("&d&lIron sword", "IRON_SWORD", 1,0, lore, 0));
        gui.setItem(22, this.swordItem("&d&lIron sword", "IRON_SWORD", 1,0, lore, 1));
        gui.setItem(31, this.swordItem("&d&lIron sword", "IRON_SWORD", 1,0, lore, 2));
        gui.setItem(40, this.swordItem("&d&lIron sword", "IRON_SWORD", 1,0, lore, 3));
        // Stone sword
        gui.setItem(14, this.swordItem("&d&lStone sword", "STONE_SWORD", 1,0, lore, 0));
        gui.setItem(23, this.swordItem("&d&lStone sword", "STONE_SWORD", 1,0, lore, 1));
        gui.setItem(32, this.swordItem("&d&lStone sword", "STONE_SWORD", 1,0, lore, 2));
        gui.setItem(41, this.swordItem("&d&lStone sword", "STONE_SWORD", 1,0, lore, 3));
        // Wood sword
        gui.setItem(15, this.swordItem("&d&lWood sword", "WOOD_SWORD", 1,0, lore, 0));
        gui.setItem(24, this.swordItem("&d&lWood sword", "WOOD_SWORD", 1,0, lore, 1));
        gui.setItem(33, this.swordItem("&d&lWood sword", "WOOD_SWORD", 1,0, lore, 2));
        gui.setItem(42, this.swordItem("&d&lWood sword", "WOOD_SWORD", 1,0, lore, 3));

        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het weapons menu"));
        gui.setItem(49, this.menuItem("&c&lTerug naar weapons &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().swordsTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().swordsSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().swordsBackgroundMaterial), plugin.getConfigManager().getGui().swordsBackgroundAmount, plugin.getConfigManager().getGui().swordsBackgroundShort);
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

    public ItemStack swordItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore, int version) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        if (!material.equalsIgnoreCase("barrier")) {
            if (version == 1) {
                if (material.equalsIgnoreCase("gold_sword")) {
                    MetaItem.addEnchant(Enchantment.DURABILITY, 2, true);
                } else {
                    MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
                }
            }
            if (version == 2) {
                if (material.equalsIgnoreCase("gold_sword")) {
                    MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
                    MetaItem.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                } else {
                    MetaItem.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                }
            }
            if (version == 3) {
                if (material.equalsIgnoreCase("gold_sword")) {
                    MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
                    MetaItem.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                } else {
                    MetaItem.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                }
            }
        }
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }
}
