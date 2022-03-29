package io.github.deechtezeeuw.crazemcwidm.scoreboards.game;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
        String players = "0";
        String started = "Niet gestart";

        gamemap.addEntry("§e§lMap:");
        String line = ChatColor.translateAlternateColorCodes('&', "&7" + game.getTheme().substring(0, 1).toUpperCase() + game.getTheme().substring(1));
        gamemap.setSuffix(" " + line);

        gamerole.addEntry("§e§lRol:");
        for (Contestant singleConstestant : game.getContestant()) {
            if (singleConstestant.getPlayer() == null) continue;
            if (singleConstestant.getPlayer().equals(player.getUniqueId())) {
                role = singleConstestant.getRoleName();
                kleur = " " + singleConstestant.getChatColor() + singleConstestant.getColorName();
                if (!singleConstestant.getDeath()) {
                    Integer newPlayers = Integer.parseInt(players) + 1;
                    players = newPlayers.toString();
                }
                break;
            }
        }
        if (game.getHosts().contains(player.getUniqueId())) role = "Host";
        gamerole.setSuffix(ChatColor.translateAlternateColorCodes('&', " &b"+role));

        gamekleur.addEntry("§e§lKleur:");
        gamekleur.setSuffix(ChatColor.translateAlternateColorCodes('&', kleur));

        gamespelers.addEntry("§e§lSpelers:");
        gamespelers.setSuffix(ChatColor.translateAlternateColorCodes('&', " &7"+players));

        gametime.addEntry("§e§lBezig:");
        switch (game.getGameStatus()) {
            case 1:
                started = "00:00";
                break;
            case 2:
                started = "Gepauzeerd";
                break;
            case 3:
                started = "Afgelopen";
                break;
            default:
                started = "Niet gestart";
        }
        gametime.setSuffix(ChatColor.translateAlternateColorCodes('&'," &7" + started));

        player.setScoreboard(board);

        this.update(player);
    }

    protected void update(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Default values
                String role = "Spectator";
                String kleur = " &7Nvt";
                String players = "0";
                String started = "Niet gestart";
                //methods
                if (player.getScoreboard() == null) { cancel(); return; }
                if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) { cancel(); return; }
                if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScoreboard() == null) { cancel(); return; }

                // Check if world were user is now is an game map
                if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) {
                    cancel();
                }

                // Get game
                Game game = (plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID()) != null ?  plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID()): null);
                if (game == null) cancel();

                Scoreboard board = player.getScoreboard();

                Team gamerole = board.getTeam("gamerole");
                Team gamekleur = board.getTeam("gamekleur");
                Team gamespelers = board.getTeam("gamespelers");
                Team gametime = board.getTeam("gametime");

                // players
                for (Contestant singleConstestant : game.getContestant()) {
                    if (singleConstestant.getPlayer() == null) continue;
                    if (singleConstestant.getPlayer().equals(player.getUniqueId())) {
                        role = singleConstestant.getRoleName();
                        kleur = " " + singleConstestant.getChatColor() + singleConstestant.getColorName();
                    }
                    if (!singleConstestant.getDeath()) {
                        Integer newPlayers = Integer.parseInt(players) + 1;
                        players = newPlayers.toString();
                    }
                }

                if (game.getHosts().contains(player.getUniqueId())) role = "Host";
                gamerole.setSuffix(ChatColor.translateAlternateColorCodes('&', " &b"+role));
                gamekleur.setSuffix(ChatColor.translateAlternateColorCodes('&', kleur)); // Player color
                gamespelers.setSuffix(ChatColor.translateAlternateColorCodes('&', " &7"+players)); // Player size

                // Time
                switch (game.getGameStatus()) {
                    case 1:
                        int total = plugin.getGameManager().getGamesThatStarted().get(game.getUuid());
                        int minutes = 0;
                        int seconds = 0;

                        minutes = total/60;
                        seconds = total - (minutes*60);
                        String minuteString = ""+ minutes;
                        if (minuteString.length() == 1) minuteString = "0"+minuteString;
                        if (minuteString.length() == 0) minuteString = "00";

                        String secondsString = ""+seconds;
                        if (secondsString.length() == 1) secondsString = "0"+secondsString;
                        if (secondsString.length() == 0) secondsString = "00";

                        started = minuteString + ":" + secondsString;
                        break;
                    case 2:
                        started = "Gepauzeerd";
                        break;
                    case 3:
                        started = "Afgelopen";
                        break;
                    default:
                        started = "Niet gestart";
                }
                gametime.setSuffix(ChatColor.translateAlternateColorCodes('&'," &7" + started));
            }
        }.runTaskTimer(plugin, 1, 1 * 20L);
    }
}
