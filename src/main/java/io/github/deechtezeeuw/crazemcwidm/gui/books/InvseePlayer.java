package io.github.deechtezeeuw.crazemcwidm.gui.books;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
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

public class InvseePlayer extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void openWithItems(Player player, ItemStack[] itemStacks) {
        // check if user is in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) {
            player.closeInventory();
            return;
        }

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                this.size(),
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i < this.size();i++) {
            gui.setItem(i, this.background());
        }

        for (int i = 0; i < this.size();i++) {
            if (itemStacks[i] == null) continue;
            gui.setItem(i, itemStacks[i]);
        }

        player.openInventory(gui);
    }

    @Override
    public void open(Player player) {
        return;
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&d&lPlayer inventory";
    }

    @Override
    public Integer size() {
        return 36;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 15);
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
