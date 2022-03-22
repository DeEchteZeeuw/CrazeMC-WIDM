package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class GameManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private final String[] colors = {"black", "blue", "cyan", "gray", "green", "lightblue", "lightgray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow"};

    public void createGame(Game game) {
        // Add game to database
        plugin.getSQL().sqlInsert.insertGame(game);

        int[] colorCodes = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        // Add contestants to the database
        for (Integer i = 0; i < plugin.getConfigManager().getMain().getConfig().getInt("mappen."+game.getTheme()+".max-contestants"); i++) {
            Contestant contestant = new Contestant();
            contestant.setUuid(UUID.randomUUID());
            contestant.setGame(game.getUuid());
            int number = new Random().nextInt(colorCodes.length);
            contestant.setColor(number);
            ArrayUtils.remove(colorCodes, number);

            plugin.getSQL().sqlInsert.insertContestant(contestant);
        }
    }

    public void deleteGame(Game game) {
        if (game == null) return;

        // TP all users away
        for (UUID singlePlayerInWorld : game.AllPlayersInsideGame()) {
            if (Bukkit.getServer().getPlayer(singlePlayerInWorld) != null) {
                Player singlePlayer = Bukkit.getServer().getPlayer(singlePlayerInWorld);
                if (singlePlayer.getWorld().getUID().equals(game.getMap())) singlePlayer.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            }
        }

        // Delete world
        if (game.getMap() != null) {
            if (Bukkit.getServer().getWorld(game.getMap()) != null) {
                World world = Bukkit.getServer().getWorld(game.getMap());

                ConsoleCommandSender console = Bukkit.getConsoleSender();
                String command = "mv delete "+world.getName();
                Bukkit.dispatchCommand(console, command);
                Bukkit.dispatchCommand(console, "mv confirm");
            }
        }

        // Delete contestants
        for (Contestant contestant : game.getContestant()) {
            plugin.getSQL().sqlDelete.deleteContestant(contestant.getUuid());
        }

        // Delete from database
        plugin.getSQL().sqlDelete.deleteGame(game.getUuid());
    }

    public String[] getColors() {
        return colors;
    }
}
