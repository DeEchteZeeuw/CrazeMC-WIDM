package io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.GraphicalUserInterface;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RolesMenu extends GraphicalUserInterface {
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

        // Speler
        gui.setItem(11, this.menuItem("&b&lSpeler &8>>", "PAPER", 1,0, lore));
        // Egoïst
        gui.setItem(12, this.menuItem("&b&lEgoïst &8>>", "PAPER", 1, 0, lore));
        // Mol
        gui.setItem(14, this.menuItem("&b&lMol &8>>", "PAPER", 1, 0, lore));
        // Peacekeeper
        gui.setItem(15, this.menuItem("&b&lPeacekeeper &8>>", "PAPER", 1, 0, lore));
        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het items menu"));
        gui.setItem(22, this.menuItem("&c&lTerug naar items &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().rolesTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().rolesSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().rolesBackgroundMaterial), plugin.getConfigManager().getGui().rolesBackgroundAmount, plugin.getConfigManager().getGui().rolesBackgroundShort);
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
