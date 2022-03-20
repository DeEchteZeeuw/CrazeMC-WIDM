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

public class MapMenu extends GraphicalUserInterface {
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

        // Loop through founded maps in the config
        for (int i =0; i < plugin.getConfigManager().getMain().mappen.size();i++) {
            String path = plugin.getConfigManager().getMain().mappen.get(i); // Get path (mappen.<name>)
            // Check if active or not if not then skip this item
            if (!plugin.getConfigManager().getMain().getConfig().getBoolean(path+".active")) continue;

            boolean hasPermission = false;
            // Loop through the permission that you need for this map
            for (String permission : plugin.getConfigManager().getMain().getConfig().getStringList(path+".permission")) {
                if (player.hasPermission(permission)) hasPermission = true; // If user has permission set hasPermission to true
            }
            // If players did not have the permission then skip this item
            if (!hasPermission) continue;
            // Check if map is active to use or not
            boolean active = plugin.getConfigManager().getMain().getConfig().getBoolean(path+".active");
            // Get the title of the map for displaying
            String title = plugin.getConfigManager().getMain().getConfig().getString(path+".title");
            // Check if the world exists in the server
            boolean world = Bukkit.getServer().getWorld(plugin.getConfigManager().getMain().getConfig().getString(path + ".world")) != null;
            // If the map isnt active or the world did not exist then set code to Red wool (14) if both are true then set it to Lime wool (5)
            Integer code = (!active || !world) ? 14 : 5;

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&dKlik om deze map te kiezen!"));
            // Place item
            gui.setItem(10+i, this.menuItem(title, "WOOL", 1, code, lore));
        }

        // Set explain items
        ArrayList<String> lore = new ArrayList<>();
        gui.setItem(37, this.menuItem("&a&lMap beschikbaar", "WOOL", 1, 5, lore));
        gui.setItem(38, this.menuItem("&c&lMap niet beschikbaar", "WOOL", 1, 14, lore));

        if (plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&dKlik om je huidige game te unclaimen!"));
            gui.setItem(43, this.menuItem("&e&lUnclaim", "WOOL", 1, 4, lore));
        }

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().mapTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().mapSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().mapBackgroundMaterial), plugin.getConfigManager().getGui().mapBackgroundAmount, plugin.getConfigManager().getGui().mapBackgroundShort);
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
