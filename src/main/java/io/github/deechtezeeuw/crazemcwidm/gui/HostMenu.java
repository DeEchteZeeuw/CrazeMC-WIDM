package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class HostMenu extends GraphicalUserInterface {
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

        ArrayList<String> lore = new ArrayList<>();

        // Tempvuller
        lore.add(ChatColor.translateAlternateColorCodes('&',"&7Klik om een map te vullen!"));
        gui.setItem(10, this.menuItem("&e>> &e&lTempvuller &e<<", "WOOL", 1, 13, lore));
        // JrGamehost
        gui.setItem(12, this.menuItem("&e>> &e&lJr Gamehost &e<<", "WOOL", 1, 5, lore));
        // Gamehost
        gui.setItem(14, this.menuItem("&e>> &e&lGamehost &e<<", "WOOL", 1, 1, lore));
        // SrGamehost
        gui.setItem(16, this.menuItem("&e>> &e&lSr Gamehost &e<<", "WOOL", 1, 14, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().hostTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().hostSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().hostBackgroundMaterial), plugin.getConfigManager().getGui().hostBackgroundAmount, plugin.getConfigManager().getGui().hostBackgroundShort);
        ItemMeta MetaBackgroundItem = BackgroundItem.getItemMeta();
        ArrayList<String> BackgroundItemLore = new ArrayList<String>();
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
