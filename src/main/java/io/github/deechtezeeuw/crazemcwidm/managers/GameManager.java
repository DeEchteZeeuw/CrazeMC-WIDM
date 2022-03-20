package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class GameManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void createGame(Game game) {
        // Add game to database
        plugin.getSQL().sqlInsert.insertGame(game);
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

        // Delete from database
        plugin.getSQL().sqlDelete.deleteGame(game.getUuid());
    }
}
