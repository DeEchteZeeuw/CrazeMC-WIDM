package io.github.deechtezeeuw.crazemcwidm.scoreboards.game;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameSC {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void give(Player player) {
        Game game = null;

        if (plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) {
            game = plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID());
        }

        if (game == null) return;

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("sidebar", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&m---[&5&lCraze&d&lMC&7&m]---"));

        Team gamemap = board.registerNewTeam("gamemap");
        Team gamerole = board.registerNewTeam("gamerole");
        Team gamekleur = board.registerNewTeam("gamekleur");
        Team gamespelers = board.registerNewTeam("gamespelers");
        Team gametime = board.registerNewTeam("gametime");

        obj.getScore("§e§lMap:").setScore(5);
        obj.getScore("§e§lRol:").setScore(4);
        obj.getScore("§e§lKleur:").setScore(3);
        obj.getScore("§e§lSpelers:").setScore(2);
        obj.getScore("§e§lBezig:").setScore(1);
        obj.getScore(ChatColor.translateAlternateColorCodes('&', "&7play.crazemc.com")).setScore(0);

        // Default values
        String role = "Spectator";
        String kleur = " &7Nvt";

        gamemap.addEntry("§e§lMap:");
        String line = ChatColor.translateAlternateColorCodes('&', "&7" + game.getTheme().substring(0, 1).toUpperCase() + game.getTheme().substring(1));
        gamemap.setSuffix(" " + line);

        gamerole.addEntry("§e§lRol:");
        for (Contestant singleConstestant : game.getContestant()) {
            if (singleConstestant.getPlayer() == null) continue;
            if (singleConstestant.getPlayer().equals(player.getUniqueId())) {
                role = singleConstestant.getRoleName();
                kleur = " " + singleConstestant.getChatColor() + singleConstestant.getColorName();
                break;
            }
        }
        if (game.getHosts().contains(player.getUniqueId())) role = "Host";
        gamerole.setSuffix(ChatColor.translateAlternateColorCodes('&', " &b"+role));

        gamekleur.addEntry("§e§lKleur:");
        gamekleur.setSuffix(ChatColor.translateAlternateColorCodes('&', kleur));

        gamespelers.addEntry("§e§lSpelers:");
        gamespelers.setSuffix(ChatColor.translateAlternateColorCodes('&', " &70"));

        gametime.addEntry("§e§lBezig:");
        gametime.setSuffix(ChatColor.translateAlternateColorCodes('&'," &7Niet gestart"));

        player.setScoreboard(board);

    }
}
