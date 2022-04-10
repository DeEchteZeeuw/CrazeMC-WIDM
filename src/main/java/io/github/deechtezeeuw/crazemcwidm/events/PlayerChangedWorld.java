package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.scoreboards.game.GameSC;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.UUID;

public class PlayerChangedWorld implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();

        Game game = (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) ? plugin.getGameDataManager().getWorldGame(player.getWorld().getUID()) : null;
        // Not a part of an game
        if (game == null) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            return;
        }

        new GameSC().give(player);

        for (UUID singleHost : game.getHosts()) {
            if (player.getUniqueId().equals(singleHost)) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (player.getGameMode().equals(GameMode.CREATIVE)) {
                            cancel();
                            return;
                        }
                        player.setGameMode(GameMode.CREATIVE);
                    }
                }.runTaskTimer(plugin, 1, 1 * 20L);
                return;
            }
        }
        // If player
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(player.getUniqueId())) {
                player.setGameMode(GameMode.ADVENTURE);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                            cancel();
                            return;
                        }
                        player.setGameMode(GameMode.SURVIVAL);
                    }
                }.runTaskTimer(plugin, 1, 1 * 20L);
                return;
            }
        }

        player.setGameMode(GameMode.SPECTATOR);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    cancel();
                    return;
                }
                player.setGameMode(GameMode.SPECTATOR);
            }
        }.runTaskTimer(plugin, 1, 1 * 20L);
        return;
    }
}
