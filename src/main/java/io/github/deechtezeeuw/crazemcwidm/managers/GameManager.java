package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private final ArrayList<UUID> queue = new ArrayList<>();
    private final String[] colors = {"black", "blue", "cyan", "gray", "green", "lightblue", "lightgray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow"};
    private HashMap<UUID, Integer> gamesThatStarted = new HashMap<>();

    public void createGame(Game game) {
        // Add game to database
        plugin.getSQL().sqlInsert.insertGame(game);

        ArrayList<Integer> colorCodes = new ArrayList<>();
        for (int x = 0;x < 15;x++) {
            colorCodes.add(x);
        }
        // Add contestants to the database
        for (Integer i = 0; i < plugin.getConfigManager().getMain().getConfig().getInt("mappen."+game.getTheme()+".max-contestants"); i++) {
            Contestant contestant = new Contestant();
            contestant.setUuid(UUID.randomUUID());
            contestant.setGame(game.getUuid());

            // Get random color
            if (colorCodes.size() > 0) {
                Collections.shuffle(colorCodes);
                contestant.setColor(colorCodes.get(0));
                colorCodes.remove(0);
            }

            plugin.getSQL().sqlInsert.insertContestant(contestant);
        }
    }

    public void deleteGame(Game game) {
        if (game == null) return;

        // TP all users away
        for (Player userInWorld : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
            userInWorld.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
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

    public ArrayList<UUID> getQueue() {
        return queue;
    }

    public void setQueue(UUID uuid) {
        if (queue.contains(uuid)) {
            queue.remove(uuid);
        } else {
            queue.add(uuid);
        }
    }

    public HashMap<UUID, Integer> getGamesThatStarted() {
        return gamesThatStarted;
    }

    public void setGamesThatStarted(UUID game) {
        if (gamesThatStarted.containsKey(game)) {
            gamesThatStarted.remove(game);
        } else {
            gamesThatStarted.put(game, 0);
        }
    }
}
