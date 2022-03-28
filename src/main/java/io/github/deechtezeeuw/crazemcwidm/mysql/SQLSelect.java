package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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

    // Check if user is an contestant
    public boolean playerIsContestant(UUID uuid) {
        ArrayList<Game> gameArrayList = this.gameList();

        if (gameArrayList == null || gameArrayList.size() <= 0) return false;

        for (Game singleGame : gameArrayList) {
            for (Contestant singleContestant : singleGame.getContestant()) {
                if (singleContestant.getPlayer() != null) {
                    if (singleContestant.getPlayer().equals(uuid)) return true;
                }
            }
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

    // check if user is in a game
    public boolean playerIsInAGame(UUID uuid) {
        if (this.playerIsHost(uuid)) return true;
        if (this.playerIsContestant(uuid)) return true;
        return false;
    }

    // Get game that user is in
    public Game playerContestantGame(UUID player) {
        if (player == null) return null;
        Game game = null;

        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_games WHERE Player=?");
            ps.setString(1, player.toString());
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                // Set game UUID
                if (resultSet.getString("UUID") != null) {
                    game = new Game(UUID.fromString(resultSet.getString("UUID")));
                }
                // Set real map UUID
                if (resultSet.getString("Map") != null) {
                    UUID uuidWorld = UUID.fromString(resultSet.getString("Map"));
                    if (Bukkit.getServer().getWorld(uuidWorld) != null) {
                        game.setMap(uuidWorld);
                    }
                }
                // Set all hosts
                if (resultSet.getString("Hosts") != null) {
                    String stringHosts = resultSet.getString("Hosts");
                    stringHosts = stringHosts.replaceAll("\\[", "").replaceAll("\\]", "");

                    for (String playerStr : stringHosts.split(",\\s*")) {
                        if (player == null) continue;
                        game.setHost(UUID.fromString(playerStr));
                    }
                }
                // Set theme
                if (resultSet.getString("Theme") != null) {
                    game.setTheme(resultSet.getString("Theme"));
                }
                // Set Status
                if (resultSet.getString("GameStatus") != null) {
                    game.setGameStatus(resultSet.getInt("GameStatus"));
                }
                game.setContestantsList(this.contestants(game));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return game;
    }

    // Get game from world
    public Game worldGame(UUID world) {
        if (world == null) return null;
        Game game = null;

        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_games WHERE Map=?");
            ps.setString(1, world.toString());
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                // Set game UUID
                if (resultSet.getString("UUID") != null) {
                    game = new Game(UUID.fromString(resultSet.getString("UUID")));
                }
                // Set real map UUID
                if (resultSet.getString("Map") != null) {
                    UUID uuidWorld = UUID.fromString(resultSet.getString("Map"));
                    if (Bukkit.getServer().getWorld(uuidWorld) != null) {
                        game.setMap(uuidWorld);
                    }
                }
                // Set all hosts
                if (resultSet.getString("Hosts") != null) {
                    String stringHosts = resultSet.getString("Hosts");
                    stringHosts = stringHosts.replaceAll("\\[", "").replaceAll("\\]", "");

                    for (String playerStr : stringHosts.split(",\\s*")) {
                        game.setHost(UUID.fromString(playerStr));
                    }
                }
                // Set theme
                if (resultSet.getString("Theme") != null) {
                    game.setTheme(resultSet.getString("Theme"));
                }
                // Set Status
                if (resultSet.getString("GameStatus") != null) {
                    game.setGameStatus(resultSet.getInt("GameStatus"));
                }
                game.setContestantsList(this.contestants(game));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return game;
    }

    // Get game that user is hosting
    public Game playerHostGame(UUID uuid) {
        Game game = null;
        if (playerIsHost(uuid)) {
            for (Game singleGame : gameList()) {
                for (UUID singleGameHost : singleGame.getHosts()) {
                    if (singleGameHost.equals(uuid)) {
                        return singleGame;
                    }
                }
            }
        }

        return game;
    }

    // Get game that user is playing
    public Game playerGame(UUID uuid) {
        Game game = null;
        if (playerIsHost(uuid)) {
            for (Game singleGame : gameList()) {
                for (UUID singleGamePlayer : singleGame.AllPlayersInsideGame()) {
                    if (singleGamePlayer.equals(uuid)) {
                        return singleGame;
                    }
                }
            }
        }

        return game;
    }

    // Check if contestant exists
    public boolean contestantExists(UUID contestant) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_contestants WHERE UUID=?");
            ps.setString(1, contestant.toString());

            ResultSet resultSet = ps.executeQuery();
            return  (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean contestantExists(UUID game, Integer Color) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_contestants WHERE Game=? AND Color=?");
            ps.setString(1, game.toString());
            ps.setInt(2, Color);

            ResultSet resultSet = ps.executeQuery();
            return  (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    // Check if game exists in world
    public boolean mapExists(UUID world) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_games WHERE Map=?");
            ps.setString(1, world.toString());

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
                // Set theme
                if (resultSet.getString("Theme") != null) {
                    newGame.setTheme(resultSet.getString("Theme"));
                }
                // Set Status
                if (resultSet.getString("GameStatus") != null) {
                    newGame.setGameStatus(resultSet.getInt("GameStatus"));
                }
                newGame.setContestantsList(this.contestants(newGame));
                allGames.add(newGame);
            }

            return allGames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all contestants of an game
    public ArrayList<Contestant> contestants(Game game) {
        ArrayList<Contestant> contestantArrayList = new ArrayList<>();

        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM widm_contestants WHERE Game=? ORDER BY Color ASC");
            ps.setString(1, game.getUuid().toString());

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Contestant contestant = new Contestant();
                // UUID
                if (resultSet.getString("UUID") == null) continue;
                contestant.setUuid(UUID.fromString(resultSet.getString("UUID")));
                // Game
                if (resultSet.getString("Game") == null) continue;
                contestant.setGame(UUID.fromString(resultSet.getString("Game")));
                // Color
                if (resultSet.getString("Color") == null) continue;
                contestant.setColor(resultSet.getInt("Color"));
                // Player
                if (resultSet.getString("Player") != null) {
                    contestant.setPlayer(UUID.fromString(resultSet.getString("Player")));
                }
                // Rol
                if (resultSet.getString("Role") != null) {
                    contestant.setRole(resultSet.getInt("Role"));
                }
                // Kills
                if (resultSet.getString("Kills") != null) {
                    contestant.setKills(resultSet.getInt("Kills"));
                }
                // Death
                if (resultSet.getString("Death") != null) {
                    contestant.setDeath(resultSet.getBoolean("Death"));
                }
                // Peacekeeper
                if (resultSet.getString("Peacekeeper") != null) {
                    contestant.setPeacekeeper(resultSet.getBoolean("Peacekeeper"));
                }
                // PKKills
                if (resultSet.getString("PKKills") != null) {
                    contestant.setPeacekeeperKills(resultSet.getInt("PKKills"));
                }
                // Spawn
                if (resultSet.getString("Spawn") != null) {
                    String[] locationStr = resultSet.getString("Spawn").split("#");
                    World world = Bukkit.getServer().getWorld(locationStr[0]);
                    int blockX = Integer.parseInt(locationStr[1]);
                    int blockY = Integer.parseInt(locationStr[2]);
                    int blockZ = Integer.parseInt(locationStr[3]);
                    float pitch = Float.parseFloat(locationStr[4]);
                    float yawn = Float.parseFloat(locationStr[5]);

                    Location location = new Location(world, blockX, blockY, blockZ);
                    location.setPitch(pitch);
                    location.setYaw(yawn);
                    contestant.setSpawn(location);
                }
                contestantArrayList.add(contestant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return contestantArrayList;
    }
}
