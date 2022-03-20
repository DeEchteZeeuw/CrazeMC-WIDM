package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SQLSelect {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    // Check if player exists
    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM players WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet resultSet = ps.executeQuery();
            return  (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if user is an host
    public boolean playerIsHost(UUID uuid) {
        ArrayList<Game> gameArrayList = this.gameList();

        if (gameArrayList == null || gameArrayList.size() <= 0) return false;

        for (Game singleGame : gameArrayList) {
            for (UUID singleGameHost : singleGame.getHosts()) {
                if (singleGameHost.equals(uuid)) return true;
            }
        }
        return false;
    }

    // Get game that user is hosting
    public Game playerHostGame(UUID uuid) {
        Game game = null;
        if (playerIsHost(uuid)) {
            for (Game singleGame : gameList()) {
                for (UUID singleGameHost : singleGame.getHosts()) {
                    if (singleGameHost.equals(uuid)) {
                        game = singleGame;
                        break;
                    }
                }
            }
        }

        return game;
    }

    // Check if game exists
    public boolean gameExists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_games WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet resultSet = ps.executeQuery();
            return  (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all games
    public ArrayList<Game> gameList() {
        ArrayList<Game> allGames = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_games");

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Game newGame = null;
                // Set game UUID
                if (resultSet.getString("UUID") != null) {
                    newGame = new Game(UUID.fromString(resultSet.getString("UUID")));
                }
                // Set real map UUID
                if (resultSet.getString("Map") != null) {
                    UUID uuidWorld = UUID.fromString(resultSet.getString("Map"));
                    if (Bukkit.getServer().getWorld(uuidWorld) != null) {
                        newGame.setMap(uuidWorld);
                    }
                }
                // Set all hosts
                if (resultSet.getString("Hosts") != null) {
                    String stringHosts = resultSet.getString("Hosts");
                    stringHosts = stringHosts.replaceAll("\\[", "").replaceAll("\\]", "");

                    for (String player : stringHosts.split(",\\s*")) {
                        if (player == null) continue;
                        newGame.setHost(UUID.fromString(player));
                    }
                }
                allGames.add(newGame);
            }

            return allGames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
