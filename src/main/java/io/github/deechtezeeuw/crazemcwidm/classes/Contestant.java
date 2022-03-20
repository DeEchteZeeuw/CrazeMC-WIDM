package io.github.deechtezeeuw.crazemcwidm.classes;

import org.bukkit.Location;

import java.util.UUID;

public class Contestant {
    protected UUID uuid; // Unique contestant id
    protected String color; // The color which the contestant is.. Like: blue / black
    protected String chatColor; // The chat color code which the contestant is.. like for white: &f
    protected UUID player; // Unique ID of the player
    protected String role; // The role of the user.. Speler, Egoïst or Mol
    protected Integer kills; // The kills the player has made the entire game..
    protected Boolean death; // True if the player is death or false if its negative
    protected Boolean peacekeeper; // True if the player is the peacekeeper or false if its negative
    protected Integer peacekeeperKills; // The kills of the player has made while he was peacekeeper
    protected Location spawn;

    /*
    * Contestant UUID
    */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    // Contestant color
    public void setColor(String color) {
        color = color;
    }

    public String getColor() {
        return color;
    }

    // Contestant chatColor
    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public String getChatColor() {
        return chatColor;
    }

    // Contestant player
    public void setPlayer(UUID player) {
        this.player = player;
    }

    public UUID getPlayer() {
        return player;
    }

    // Contestant role
    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
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
