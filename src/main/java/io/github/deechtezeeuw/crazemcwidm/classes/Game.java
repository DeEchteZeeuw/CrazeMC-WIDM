package io.github.deechtezeeuw.crazemcwidm.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Game {
    protected UUID uuid;
    protected UUID map;
    protected ArrayList<UUID> hosts = new ArrayList<>();

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

    public ArrayList<UUID> AllPlayersInsideGame() {
        ArrayList<UUID> uuidArrayList = new ArrayList<>();
        for (UUID singleHost : hosts) {
            uuidArrayList.add(singleHost);
        }

        return uuidArrayList;
    }
}
