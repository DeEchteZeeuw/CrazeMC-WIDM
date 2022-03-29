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

public class BooksMenu extends GraphicalUserInterface {
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

        // Teleport
        gui.setItem(10, this.menuItem("&b&lTeleport &8>>", "BOOK", 1, 0, lore));
        // Switch
        gui.setItem(11, this.menuItem("&2&lSwitch &8>>", "BOOK", 1, 0, lore));
        // Invsee
        gui.setItem(12, this.menuItem("&d&lInvsee &8>>", "BOOK", 1, 0, lore));
        // Itemcheck
        gui.setItem(13, this.menuItem("&a&lItemcheck &8>>", "BOOK", 1, 0, lore));
        // Itemclear
        gui.setItem(14, this.menuItem("&f&lItemclear &8>>", "BOOK", 1, 0, lore));
        // PK Check
        gui.setItem(15, this.menuItem("&1&lPK Check &8>>", "BOOK", 1, 0, lore));
        // Book lock
        gui.setItem(16, this.menuItem("&7&lBook lock &8>>", "BOOK", 1, 0, lore));
        // Speler count
        gui.setItem(19, this.menuItem("&c&lSpeler count &8>>", "BOOK", 1, 0, lore));
        // Reborn
        gui.setItem(20, this.menuItem("&6&lReborn &8>>", "BOOK", 1, 0, lore));
        // Deathnote
        gui.setItem(21, this.menuItem("&4&lDeathnote &8>>", "BOOK", 1, 0, lore));

        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het items menu"));
        gui.setItem(31, this.menuItem("&c&lTerug naar items &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().booksTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().booksSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().booksBackgroundMaterial), plugin.getConfigManager().getGui().booksBackgroundAmount, plugin.getConfigManager().getGui().booksBackgroundShort);
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
            MetaItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }
}
