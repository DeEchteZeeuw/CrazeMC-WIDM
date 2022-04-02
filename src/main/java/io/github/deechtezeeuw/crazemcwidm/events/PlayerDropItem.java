package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        Item item = e.getItemDrop();
        // Check if item contains lore
        if (item.getItemStack().getItemMeta() == null) return;
        if (item.getItemStack().getItemMeta().getLore() == null) return;
        boolean containsLore = false;
        for (String lore : item.getItemStack().getItemMeta().getLore()) {
            if (ChatColor.stripColor(lore).replaceAll(">> ", "").equalsIgnoreCase("undroppable") || ChatColor.stripColor(lore).replaceAll(">> ", "").equalsIgnoreCase("locked")) containsLore = true;
        }
        if (!containsLore) return;

        // Check if user is not an contestant
        if (!plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) return;
        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());

        // Check if its not in the game world
        if (!player.getWorld().getUID().equals(game.getMap())) return;

        e.setCancelled(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe mag dit item niet droppen!"));
    }
}
