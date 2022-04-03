package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;

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
    public void setGameHashMap(HashMap<UUID, Game> games) {
        this.gameHashMap = games;
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
}
