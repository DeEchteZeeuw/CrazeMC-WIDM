package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.classes.Vote;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private final ArrayList<UUID> queue = new ArrayList<>();
    private final String[] colors = {"black", "blue", "cyan", "gray", "green", "lightblue", "lightgray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow"};
    private final HashMap<UUID, Integer> gamesThatStarted = new HashMap<>();
    private final HashMap<UUID, UUID> teleportChoice = new HashMap<>();
    private final HashMap<UUID, Vote> gameVotes = new HashMap<UUID, Vote>();

    public void createGame(Game game) {
        ArrayList<Integer> colorCodes = new ArrayList<>();
        for (int x = 0;x < 15;x++) {
            colorCodes.add(x);
        }

        ArrayList<Contestant> contestants = new ArrayList<>();

        // Add contestants to the database
        for (int i = 0; i < plugin.getConfigManager().getMain().getConfig().getInt("mappen."+game.getTheme()+".max-contestants"); i++) {
            Contestant contestant = new Contestant();
            contestant.setUuid(UUID.randomUUID());
            contestant.setGame(game.getUuid());

            // Get random color
            if (colorCodes.size() > 0) {
                Collections.shuffle(colorCodes);
                contestant.setColor(colorCodes.get(0));
                colorCodes.remove(0);
            }

            contestants.add(contestant);
        }

        game.setContestantsList(contestants);

        // New GameDataManager version

        // Check if game exists
        if (plugin.getGameDataManager().gameExists(game.getUuid())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is zojuist een game aanmaak aanvraag geweigerd omdat het UUID al bestond!"));
            return;
        }

        // Add game to GameDataManager
        plugin.getGameDataManager().insertGame(game);
    }

    public void deleteGame(Game game) {
        if (game == null) return;

        // Delete from GameDataManager
        plugin.getGameDataManager().deleteGame(game.getUuid());
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

    public HashMap<UUID, UUID> getTeleportChoice() {return teleportChoice; }

    public void setTeleportChoice(UUID player, UUID choice) {
        if (teleportChoice.containsKey(player)) {
            teleportChoice.remove(player);
        } else {
            teleportChoice.put(player, choice);
        }
    }

    // Get all votes from your game
    public ArrayList<Vote> getGameVotes(UUID Game) {
        ArrayList<Vote> gameVotes = new ArrayList<>();

        for(Map.Entry<UUID, Vote> entry : this.gameVotes.entrySet()) {
            UUID key = entry.getKey();
            Vote vote = entry.getValue();

            if (vote.getGame() != null) {
                if (!vote.getGame().equals(Game)) continue;
            }

            gameVotes.add(vote);
        }

        return gameVotes;
    }

    public boolean hasVoted(UUID game, UUID contestant) {
        ArrayList<Vote> gameVotes = this.getGameVotes(game);

        for(Vote vote : gameVotes) {
            if (vote.getContestant() != null) {
                if (vote.getContestant().equals(contestant)) return true;
            }
        }

        return false;
    }

    public void setGameVote(Vote vote) {
        if (this.hasVoted(vote.getGame(), vote.getContestant())) return;

        this.gameVotes.put(UUID.randomUUID(), vote);
    }

    public int getVotesOnPlayer(UUID game, UUID contestant) {
        int numberOfVotes = 0;

        ArrayList<Vote> gameVotes = this.getGameVotes(game);

        for(Vote vote : gameVotes) {
            if (vote.getContestant() != null) {
                if (vote.getContestant().equals(contestant)) numberOfVotes++;
            }
        }

        return numberOfVotes;
    }

    public void resetGameVotes(UUID game) {
        for(Map.Entry<UUID, Vote> entry : this.gameVotes.entrySet()) {
            UUID key = entry.getKey();
            Vote vote = entry.getValue();

            if (vote.getGame() != null) {
                if (vote.getGame().equals(game)) this.gameVotes.remove(key);
            }
        }
    }
}
