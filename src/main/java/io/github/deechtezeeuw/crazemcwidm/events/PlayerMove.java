package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) return;

        // Player is in a world thats a game
        Game game = plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID());
        // Check if game is null
        if (game == null) return;
        // Check if map is not this world
        boolean iscontestant = false;
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(player.getUniqueId())) iscontestant = true;
        }
        if (!iscontestant) return;

        // Game is not playing so freeze users on position
        if (game.getGameStatus() != 1) {
            if (e.getFrom().getZ() != e.getTo().getZ() && e.getFrom().getX() != e.getTo().getX()) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan pas bewegen als het spel is gestart!"));
            }
        }
    }
}
