package io.github.deechtezeeuw.crazemcwidm.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class Contestant {
    protected UUID uuid; // Unique contestant id
    protected UUID game; // Unique game id
    protected Integer color; // The color which the contestant is.. Like: blue / black
    protected UUID player = null; // Unique ID of the player
    protected Integer role; // The role of the user.. Speler, Egoïst or Mol
    protected Integer kills; // The kills the player has made the entire game..
    protected Boolean death; // True if the player is death or false if its negative
    protected Boolean peacekeeper; // True if the player is the peacekeeper or false if its negative
    protected Integer peacekeeperKills; // The kills of the player has made while he was peacekeeper
    protected Location spawn = null;

    // Contestant id
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    // Contestant game
    public void setGame(UUID uuid) {
        this.game = uuid;
    }

    public UUID getGame() {
        return game;
    }

    // Contestant color
    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getColor() {
        return color;
    }

    public String getColorName() {
        switch (this.getColor()) {
            case 0:
                return "Black";
            case 1:
                return "Blue";
            case 2:
                return "Green";
            case 3:
                return "Cyan";
            case 4:
                return "Rood";
            case 5:
                return "Purple";
            case 6:
                return "Orange";
            case 7:
                return "Light gray";
            case 8:
                return "Gray";
            case 9:
                return "Light blue";
            case 10:
                return "Lime";
            case 11:
                return "Light cyan";
            case 12:
                return "Light red";
            case 13:
                return "Magenta";
            case 14:
                return "Yellow";
            default:
                return "White";
        }
    }

    public String getChatColor() {
        switch (this.getColor()) {
            case 0:
                return "&0";
            case 1:
                return "&1";
            case 2:
                return "&2";
            case 3:
                return "&3";
            case 4:
                return "&4";
            case 5:
                return "&5";
            case 6:
                return "&6";
            case 7:
                return "&7";
            case 8:
                return "&8";
            case 9:
                return "&9";
            case 10:
                return "&a";
            case 11:
                return "&b";
            case 12:
                return "&c";
            case 13:
                return "&d";
            case 14:
                return "&e";
            default:
                return "&f";
        }
    }

    // Contestant player
    public void setPlayer(UUID player) {
        this.player = player;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getPlayername() {
        if (this.getPlayer() == null) return "Leeg";
        // Online player name
        if (Bukkit.getServer().getPlayer(this.getPlayer()) != null) return Bukkit.getServer().getPlayer(this.getPlayer()).getName();
        if (Bukkit.getServer().getOfflinePlayer(this.getPlayer()) != null) return Bukkit.getServer().getOfflinePlayer(this.getPlayer()).getName();
        return "Leeg";
    }

    // Contestant role
    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    // Contestant kills
    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getKills() {
        return kills;
    }

    // Contestant death
    public void setDeath(Boolean death) {
        this.death = death;
    }

    public Boolean getDeath() {
        return death;
    }

    // Contestant peacekeeper
    public void setPeacekeeper(Boolean peacekeeper) {
        this.peacekeeper = peacekeeper;
    }

    public Boolean getPeacekeeper() {
        return peacekeeper;
    }

    // Contestant peacekeeperKills
    public void setPeacekeeperKills(Integer peacekeeperKills) {
        this.peacekeeperKills = peacekeeperKills;
    }

    public Integer getPeacekeeperKills() {
        return peacekeeperKills;
    }

    // Contestant spawn
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }
}
