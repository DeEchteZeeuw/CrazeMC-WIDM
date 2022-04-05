package io.github.deechtezeeuw.crazemcwidm.classes;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    protected UUID uuid; // Special id of the game
    protected UUID map; // Special id of the world its playing in
    protected ArrayList<UUID> hosts = new ArrayList<>(); // Arraylist of the host
    protected String theme = ""; // String for the theme map
    protected Integer gameStatus; // Status of the game
    protected ArrayList<Contestant> contestants = new ArrayList<>(); // Contestants of the game
    protected int time;

    // Default
    public Game(UUID GameKey) {
        this.uuid = GameKey;
    }

    // Set match uuid
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    // Get uuid of game
    public UUID getUuid() {
        return uuid;
    }

    // Set map uuid
    public void setMap(UUID map) {
        this.map = map;
    }

    // Get map uuid
    public UUID getMap() {
        return map;
    }

    // Set hosts
    public void setHost(UUID player) {
        if (this.hosts.contains(player)) {
            this.hosts.remove(player);
        } else {
            this.hosts.add(player);
        }
    }

    // Get hosts
    public ArrayList<UUID> getHosts() {
        return hosts;
    }

    public boolean isHost(UUID player) {
        return this.hosts.contains(player);
    }

    // Get theme
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    // Get gameStatus
    public Integer getGameStatus() {
        if (gameStatus == null) gameStatus = 0;
        return gameStatus;
    }

    public void setGameStatus(Integer gameStatus) {
        this.gameStatus = gameStatus;
    }

    // Get contestant
    public ArrayList<Contestant> getContestant() {
        ArrayList<Contestant> alphArray = new ArrayList<>();

        for (Contestant contestant : this.contestants) {
            if (alphArray.size() != 0) {
                if (alphArray.get(alphArray.size() - 1).getColor() > contestant.getColor()) {
                    alphArray.add(0, contestant);
                } else {
                    alphArray.add(contestant);
                }
            } else {
                alphArray.add(contestant);
            }
        }
        return alphArray;
    }

    public void setContestantsList(ArrayList<Contestant> contestants) {
        this.contestants = contestants;
    }

    public void updateContestant(Contestant contestant) {
        for (int i = 0; i < this.getContestant().size(); i++) {
            if (this.getContestant().get(i).getUuid().equals(contestant.getUuid())) {
                this.getContestant().remove(i);
                this.getContestant().add(contestant);
                break;
            }
        }
    }

    public Contestant getContestant(UUID player) {
        for (Contestant contestant : this.getContestant()) {
            if (contestant.getPlayer() == null) continue;
            if (contestant.getPlayer().equals(player)) return contestant;
        }

        return null;
    }

    public Contestant getContestantByID(UUID contestantID) {
        for (Contestant contestant : this.getContestant()) {
            if (contestant.getPlayer() == null) continue;
            if (contestant.getUuid().equals(contestantID)) return contestant;
        }

        return null;
    }

    public boolean isContestant(UUID player) {
        for (Contestant contestant : this.getContestant()) {
            if (contestant.getPlayer() == null) continue;
            if (contestant.getPlayer().equals(player)) return true;
        }

        return false;
    }

    // Time
    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    // Other commands
    public void updateTimer() {
        final Game game = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (game.getGameStatus() != 1) {
                    cancel();
                } else {
                    game.setTime(game.getTime() + 1);
                }
            }
        }.runTaskTimer(plugin, 1, 20L);
    }

    // All players
    public ArrayList<UUID> AllPlayersInsideGame() {

        return new ArrayList<>(hosts);
    }
}
