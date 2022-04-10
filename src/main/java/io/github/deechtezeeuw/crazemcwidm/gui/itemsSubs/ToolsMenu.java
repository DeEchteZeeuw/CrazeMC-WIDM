package io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs;

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

public class ToolsMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden die jij host, het menu sluit!"));
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld, het menu sluit!"));
            player.closeInventory();
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

        // Schaar
        gui.setItem(11, this.menuItem("&d&lSchaar &8>>", "SHEARS", 1,0, lore));
        // Vishengel
        gui.setItem(13, this.menuItem("&d&lVishengel &8>>", "FISHING_ROD", 1, 0, lore));
        // Aansteker
        gui.setItem(15, this.menuItem("&d&lAansteker &8>>", "FLINT_AND_STEEL", 1, 0, lore));
        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het items menu"));
        gui.setItem(22, this.menuItem("&c&lTerug naar items &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().toolsTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().toolsSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().toolsBackgroundMaterial), plugin.getConfigManager().getGui().toolsBackgroundAmount, plugin.getConfigManager().getGui().toolsBackgroundShort);
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
            MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        item.setDurability((short) (item.getType().getMaxDurability()-1));
        return item;
    }
}
