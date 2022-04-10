package io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.GraphicalUserInterface;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

public class NonLeatherMenu extends GraphicalUserInterface {
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

        // Black Leather
        gui.setItem(9, this.leatherItem("&0&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(0,0,0)));
        gui.setItem(18, this.leatherItem("&0&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(0,0,0)));
        gui.setItem(27, this.leatherItem("&0&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(0,0,0)));

        // Blue Leather
        gui.setItem(10, this.leatherItem("&1&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(0,0,190)));
        gui.setItem(19, this.leatherItem("&1&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(0,0,190)));
        gui.setItem(28, this.leatherItem("&1&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(0,0,190)));

        // Cyan Leather
        gui.setItem(11, this.leatherItem("&3&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(0,190,190)));
        gui.setItem(20, this.leatherItem("&3&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(0,190,190)));
        gui.setItem(29, this.leatherItem("&3&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(0,190,190)));

        // Gray Leather
        gui.setItem(12, this.leatherItem("&8&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(63,63,63)));
        gui.setItem(21, this.leatherItem("&8&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(63,63,63)));
        gui.setItem(30, this.leatherItem("&8&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(63,63,63)));

        // Green Leather
        gui.setItem(13, this.leatherItem("&2&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(0,190,0)));
        gui.setItem(22, this.leatherItem("&2&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(0,190,0)));
        gui.setItem(31, this.leatherItem("&2&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(0,190,0)));

        // Lightblue Leather
        gui.setItem(14, this.leatherItem("&b&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(63, 254, 254)));
        gui.setItem(23, this.leatherItem("&b&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(63, 254, 254)));
        gui.setItem(32, this.leatherItem("&b&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(63, 254, 254)));

        // Lightgray Leather
        gui.setItem(15, this.leatherItem("&7&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(190, 190, 190)));
        gui.setItem(24, this.leatherItem("&7&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(190, 190, 190)));
        gui.setItem(33, this.leatherItem("&7&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(190, 190, 190)));

        // Lime Leather
        gui.setItem(16, this.leatherItem("&a&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(63, 254, 63)));
        gui.setItem(25, this.leatherItem("&a&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(63, 254, 63)));
        gui.setItem(34, this.leatherItem("&a&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(63, 254, 63)));

        // Magenta Leather
        gui.setItem(17, this.leatherItem("&d&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(254, 63, 254)));
        gui.setItem(26, this.leatherItem("&d&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(254, 63, 254)));
        gui.setItem(35, this.leatherItem("&d&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(254, 63, 254)));

        // Next Arrow
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik om naar de volgende pagina te gaan"));
        gui.setItem(44, this.menuItem("&d&lVolgende pagina &8>>", "ARROW", 1, 0, lore));

        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het items menu"));
        gui.setItem(40, this.menuItem("&c&lTerug naar items &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    public void openPage(Player player, int page) {
        if (page == 0) this.open(player);

        if (page > 0) {
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

            // Orange Leather
            gui.setItem(9, this.leatherItem("&6&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(217, 163, 52)));
            gui.setItem(18, this.leatherItem("&6&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(217, 163, 52)));
            gui.setItem(27, this.leatherItem("&6&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(217, 163, 52)));

            // Pink Leather
            gui.setItem(10, this.leatherItem("&c&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(254, 63, 63)));
            gui.setItem(19, this.leatherItem("&c&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(254, 63, 63)));
            gui.setItem(28, this.leatherItem("&c&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(254, 63, 63)));

            // Purple Leather
            gui.setItem(11, this.leatherItem("&5&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(190, 0, 190)));
            gui.setItem(20, this.leatherItem("&5&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(190, 0, 190)));
            gui.setItem(29, this.leatherItem("&5&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(190, 0, 190)));

            // Red Leather
            gui.setItem(12, this.leatherItem("&4&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(190, 0, 0)));
            gui.setItem(21, this.leatherItem("&4&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(190, 0, 0)));
            gui.setItem(30, this.leatherItem("&4&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(190, 0, 0)));

            // White Leather
            gui.setItem(13, this.leatherItem("&f&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(255, 255, 255)));
            gui.setItem(22, this.leatherItem("&f&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(255, 255, 255)));
            gui.setItem(31, this.leatherItem("&f&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(255, 255, 255)));

            // Yellow Leather
            gui.setItem(14, this.leatherItem("&e&lLeather Chest &8>>", "LEATHER_CHESTPLATE", 1,0, lore, Color.fromRGB(254, 254, 63)));
            gui.setItem(23, this.leatherItem("&e&lLeather Leggings &8>>", "LEATHER_LEGGINGS", 1,0, lore, Color.fromRGB(254, 254, 63)));
            gui.setItem(32, this.leatherItem("&e&lLeather Boots &8>>", "LEATHER_BOOTS", 1,0, lore, Color.fromRGB(254, 254, 63)));

            // Previous Arrow
            lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik om naar de vorige pagina te gaan"));
            gui.setItem(36, this.menuItem("&d&lVorige pagina &8>>", "ARROW", 1, 0, lore));

            // Back to panel
            lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het items menu"));
            gui.setItem(40, this.menuItem("&c&lTerug naar items &8>>", "BARRIER", 1 , 0, lore));

            player.openInventory(gui);
        }
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().nonEnchantedLeatherGearTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().nonEnchantedLeatherGearSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().nonEnchantedGearBackgroundMaterial), plugin.getConfigManager().getGui().nonEnchantedLeatherGearBackgroundAmount, plugin.getConfigManager().getGui().nonEnchantedLeatherGearBackgroundShort);
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

    public ItemStack leatherItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore, Color color) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        LeatherArmorMeta colorMeta = (LeatherArmorMeta) item.getItemMeta();
        colorMeta.setColor(color);
        item.setItemMeta(colorMeta);
        return item;
    }
}
