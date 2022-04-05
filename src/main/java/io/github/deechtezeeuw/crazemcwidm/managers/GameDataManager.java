package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameDataManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    // All stored games
    protected HashMap<UUID, Game> gameHashMap = new HashMap<>();

    /*
    Functions of the game
     */

    // Get all games in hashmap
    public HashMap<UUID, Game> getGameHashMap() {
        return this.gameHashMap;
    }

    // Get all games in arraylist
    public ArrayList<Game> getGamesArrayList() {
        ArrayList<Game> gameArrayList = new ArrayList<>();

        for(Map.Entry<UUID, Game> entry : this.gameHashMap.entrySet()) {
            gameArrayList.add(entry.getValue());
        }

        return gameArrayList;
    }

    // Import all games from database for example
    public void setGameHashMap(ArrayList<Game> games) {
        for (Game game : games) {
            this.gameHashMap.put(game.getUuid(), game);
        }
    }

    // Check if game exists
    public boolean gameExists(UUID game) {
        return this.gameHashMap.containsKey(game);
    }

    // Insert an new game
    public void insertGame(Game game) {
        this.gameHashMap.put(game.getUuid(), game);
    }

    // Delete an game
    public void deleteGame(UUID game) {
        this.gameHashMap.remove(game);
    }

    /*
    Host functions
     */

    // Check if player is hosting an game
    public boolean alreadyHosting(UUID player) {
        for (Game game : this.getGamesArrayList()) {
            if (game.getHosts() == null) continue;
            if (game.getHosts().contains(player)) return true;
        }

        return false;
    }

    // Unclaim current hosting game
    public void unclaimHostingGame(UUID player) {
        Game game = null;
        for (Game singleGame : this.getGamesArrayList()) {
            if (singleGame.getHosts() == null) continue;
            if (singleGame.getHosts().contains(player)) {
                game = singleGame;
                break;
            }
        }

        if (game == null) return;

        // Delete local
        try {
            this.deleteGame(game.getUuid());
        } catch (Exception ex) {
            ex.printStackTrace();
            // Check if game exists
            if (!plugin.getGameDataManager().gameExists(game.getUuid())) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is zojuist een aanvraag om een game te verwijderen geweigerd, omdat hij niet lokaal bestaat!"));
                return;
            }
        }

        // Delete in database
        try {
            plugin.getSQL().sqlDelete.deleteGame(game.getUuid());
            for (Contestant contestant : game.getContestant()) {
                if (contestant == null) continue;
                if (plugin.getSQL().sqlSelect.contestantExists(contestant.getUuid())) {
                    plugin.getSQL().sqlDelete.deleteContestant(contestant.getUuid());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Check if game exists
            if (!plugin.getGameDataManager().gameExists(game.getUuid())) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is zojuist een aanvraag om een game te verwijderen geweigerd, omdat hij niet bestaat in de database!"));
                return;
            }
        }

        // Delete map
        if (game.getMap() != null) {
            if (Bukkit.getServer().getWorld(game.getMap()) != null) {
                World world = Bukkit.getServer().getWorld(game.getMap());

                ConsoleCommandSender console = Bukkit.getConsoleSender();
                String command = "mv delete "+world.getName();
                Bukkit.dispatchCommand(console, command);
                Bukkit.dispatchCommand(console, "mv confirm");
            }
        }

        if (game.getMap() == null || Bukkit.getServer().getWorld(game.getMap()) == null) return;
        World world = Bukkit.getServer().getWorld(game.getMap());
        World lobbyWorld = (Bukkit.getServer().getWorld("WIDM-Lobby") == null) ? Bukkit.getServer().getWorlds().get(0) : Bukkit.getServer().getWorld("WIDM-Lobby");
        for (Player singlePlayer : world.getPlayers()) {
            singlePlayer.teleport(lobbyWorld.getSpawnLocation());
            singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aDe game waarin je zat is geunclaimed! Je wordt naar de lobby geteleporteerd."));
        }

        if (!Bukkit.getServer().getPlayer(player).getWorld().getUID().equals(game.getMap())) {
            Bukkit.getServer().getPlayer(player).sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de game geunclaimed!"));
        }
    }

    public Game getHostingGame(UUID player) {
        Game game = null;
        for (Game singleGame : this.getGamesArrayList()) {
            if (singleGame.getHosts() == null) continue;
            if (singleGame.getHosts().contains(player)) {
                game = singleGame;
                break;
            }
        }

        return game;
    }

    /*
    World functions
     */

    // Check if the world is from a game
    public boolean worldIsPartOfGame(UUID world) {
        for (Game game : this.getGamesArrayList()) {
            if (game.getMap() == null) continue;
            if (game.getMap().equals(world)) return true;
        }

        return false;
    }

    // Get game
    public Game getWorldGame(UUID world) {
        for (Game game : this.getGamesArrayList()) {
            if (game.getMap() == null) continue;
            if (game.getMap().equals(world)) return game;
        }

        return null;
    }

    /*
    Contestant functions
     */

    // Check if player is an contestant somewhere
    public boolean alreadyContestant(UUID player) {
        for (Game game : this.getGamesArrayList()) {
            if (game.getContestant() == null) continue;
            for (Contestant contestant : game.getContestant()) {
                if (contestant.getPlayer() == null) continue;
                if (contestant.getPlayer().equals(player)) return true;
            }
        }

        return false;
    }

    public Game getContestingGame(UUID player) {
        for (Game game : this.getGamesArrayList()) {
            if (game.getContestant() == null) continue;
            for (Contestant contestant : game.getContestant()) {
                if (contestant.getPlayer() == null) continue;
                if (contestant.getPlayer().equals(player)) return game;
            }
        }
        return null;
    }

    public Contestant getContestingContest(UUID game, UUID player) {
        for (Contestant contestant : this.gameHashMap.get(game).getContestant()) {
            if (contestant.getPlayer() == null) continue;
            if (contestant.getPlayer().equals(player)) return contestant;
        }
        return null;
    }

    /*
    Backup functions
     */

    public void updateDatabase(boolean legend) {
        try {
            for (Game game : this.getGamesArrayList()) {
                if (legend && game.getGameStatus() != 3) game.setGameStatus(2);
                if (plugin.getSQL().sqlSelect.gameExists(game.getUuid())) {
                    // Game exists so update
                    plugin.getSQL().sqlUpdate.updateGame(game, "all");
                } else {
                    // Game not exists so insert
                    plugin.getSQL().sqlInsert.insertGame(game);
                }
                // Loop through the contestant
                for (Contestant contestant : game.getContestant()) {
                    if (!plugin.getSQL().sqlSelect.contestantExists(contestant.getUuid())) {
                        plugin.getSQL().sqlInsert.insertContestant(contestant);
                    } else {
                        plugin.getSQL().sqlUpdate.updateContestant(contestant, "all");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan tijdens het updaten van de game(s) naar de database!"));
            return;
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de game(s) geupdate naar de database!"));
    }
}
