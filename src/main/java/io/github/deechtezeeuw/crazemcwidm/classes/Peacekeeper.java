package io.github.deechtezeeuw.crazemcwidm.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Peacekeeper {
    public ItemStack helmet() {
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1, (short) 1);
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        MetaItem.addEnchant(Enchantment.DURABILITY, 20, true);
        MetaItem.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&b&lPeacekeeper Helmet &8>>"));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack chestplate() {
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1, (short) 1);
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        MetaItem.addEnchant(Enchantment.DURABILITY, 20, true);
        MetaItem.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&b&lPeacekeeper Chestplate &8>>"));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack leggings() {
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1, (short) 1);
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        MetaItem.addEnchant(Enchantment.DURABILITY, 20, true);
        MetaItem.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&b&lPeacekeeper Leggings &8>>"));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack boots() {
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1, (short) 1);
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        MetaItem.addEnchant(Enchantment.DURABILITY, 20, true);
        MetaItem.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&b&lPeacekeeper Boots &8>>"));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack sword() {
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1, (short) 1);
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        MetaItem.addEnchant(Enchantment.DURABILITY, 20, true);
        MetaItem.addEnchant(Enchantment.DAMAGE_ALL, 20, true);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&b&lPeacekeeper Sword &8>>"));
        item.setItemMeta(MetaItem);
        return item;
    }
}
