package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
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

public class ItemsMenu extends GraphicalUserInterface {
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

        ArrayList<String> lore = null;
        // Enchanted gear
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om enchanted gear te kiezen"));
        gui.setItem(10,this.menuItem("&d&lEnchanted gear &8>>", "DIAMOND_CHESTPLATE", 1 , 0, lore));
        // Non enchanted gear
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om non enchanted gear te kiezen"));
        gui.setItem(12, this.menuItem("&d&lNon enchanted gear &8>>", "DIAMOND_CHESTPLATE", 1 , 0, lore));
        // Enchanted leather gear
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om enchanted leren gear te kiezen"));
        gui.setItem(14, this.menuItem("&d&lEnchanted leather gear &8>>", "LEATHER_CHESTPLATE", 1 , 0, lore));
        // Non enchanted leather gear
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om non enchanted leren gear te kiezen"));
        gui.setItem(16, this.menuItem("&d&lNon enchanted leather gear &8>>", "LEATHER_CHESTPLATE", 1 , 0, lore));
        // Weapons
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om wapens te kiezen"));
        gui.setItem(20, this.menuItem("&d&lWeapons &8>>", "DIAMOND_SWORD", 1 , 0, lore));
        // Roles
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om rollen te kiezen"));
        gui.setItem(22, this.menuItem("&d&lRoles &8>>", "PAPER", 1 , 0, lore));
        // Tools
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om gereedschappen te kiezen"));
        gui.setItem(24, this.menuItem("&d&lTools &8>>", "DIAMOND_PICKAXE", 1 , 0, lore));
        // Blocks
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om blokken te kiezen"));
        gui.setItem(30, this.menuItem("&d&lBlocks &8>>", "GRASS", 1 , 0, lore));
        // Books
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om boeken te kiezen"));
        gui.setItem(32, this.menuItem("&d&lBooks &8>>", "BOOK", 1 , 0, lore));
        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het panel"));
        gui.setItem(40, this.menuItem("&c&lTerug naar panel &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().itemsTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().itemsSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().itemsBackgroundMaterial), plugin.getConfigManager().getGui().itemsBackgroundAmount, plugin.getConfigManager().getGui().itemsBackgroundShort);
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
        if (material.equalsIgnoreCase("book") || material.equalsIgnoreCase("diamond_chestplate") || material.equalsIgnoreCase("leather_chestplate")) {
            if (!title.contains("Non")) {
                MetaItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                MetaItem.addEnchant(Enchantment.DURABILITY, 5, true);
            }
        }
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }
}
