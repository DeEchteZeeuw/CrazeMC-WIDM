package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        // Set new spawn to lobby
        player.setBedSpawnLocation(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        // Check if player is in queue then remove from queue
        if (plugin.getGameManager().getQueue().contains(player.getUniqueId())) {
            plugin.getGameManager().setQueue(player.getUniqueId());
        }
    }
}
