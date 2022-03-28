package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.scoreboards.game.GameSC;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerChangedWorld implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();

        World world = player.getWorld();
        Game game = null;
        // Check if world is part of an game
        for (Game singleGame : plugin.getSQL().sqlSelect.gameList()) {
            if (singleGame.getMap().equals(world.getUID())) {
                game = singleGame;
                break;
            }
        }
        // Not a part of an game
        if (game == null) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            return;
        }

        new GameSC().give(player);
    }
}
