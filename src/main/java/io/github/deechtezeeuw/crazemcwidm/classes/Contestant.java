package io.github.deechtezeeuw.crazemcwidm.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class Contestant {
    protected UUID uuid; // Unique contestant id
    protected UUID game; // Unique game id
    protected Integer color; // The color which the contestant is.. Like: blue / black
    protected UUID player = null; // Unique ID of the player
    protected Integer role; // The role of the user.. Onbekend, Speler, Egoïst or Mol
    protected Integer kills; // The kills the player has made the entire game..
    protected Boolean death; // True if the player is death or false if its negative
    protected Boolean peacekeeper; // True if the player is the peacekeeper or false if its negative
    protected Integer peacekeeperKills; // The kills of the player has made while he was peacekeeper
    protected Location spawn = null;
    protected boolean booklock;

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
                return "Cyan";
            case 3:
                return "Gray";
            case 4:
                return "Green";
            case 5:
                return "Lightblue";
            case 6:
                return "Lightgray";
            case 7:
                return "Lime";
            case 8:
                return "Magenta";
            case 9:
                return "Orange";
            case 10:
                return "Pink";
            case 11:
                return "Purple";
            case 12:
                return "Red";
            case 13:
                return "White";
            case 14:
                return "Yellow";
            default:
                return "Fout";
        }
    }

    public String getChatColor() {
        switch (this.getColor()) {
            case 0:
                return "&0";
            case 1:
                return "&1";
            case 2:
                return "&3";
            case 3:
                return "&8";
            case 4:
                return "&2";
            case 5:
                return "&b";
            case 6:
                return "&7";
            case 7:
                return "&a";
            case 8:
                return "&d";
            case 9:
                return "&6";
            case 10:
                return "&c";
            case 11:
                return "&5";
            case 12:
                return "&4";
            case 13:
                return "&f";
            case 14:
                return "&e";
            default:
                return "&c&m";
        }
    }

    // Contestant get shulker
    public Material getShulkerMaterial() {
        switch (this.getColor()) {
            case 0:
                return Material.BLACK_SHULKER_BOX;
            case 1:
                return Material.BLUE_SHULKER_BOX;
            case 2:
                return Material.CYAN_SHULKER_BOX;
            case 3:
                return Material.GRAY_SHULKER_BOX;
            case 4:
                return Material.GREEN_SHULKER_BOX;
            case 5:
                return Material.LIGHT_BLUE_SHULKER_BOX;
            case 6:
                return Material.SILVER_SHULKER_BOX;
            case 7:
                return Material.LIME_SHULKER_BOX;
            case 8:
                return Material.MAGENTA_SHULKER_BOX;
            case 9:
                return Material.ORANGE_SHULKER_BOX;
            case 10:
                return Material.PINK_SHULKER_BOX;
            case 11:
                return Material.PURPLE_SHULKER_BOX;
            case 12:
                return Material.RED_SHULKER_BOX;
            case 13:
                return Material.WHITE_SHULKER_BOX;
            case 14:
                return Material.YELLOW_SHULKER_BOX;
            case 15:
                return Material.BARRIER;
        }
        return Material.BARRIER;
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

    public String getRoleName() {
        switch (this.getRole()) {
            case 1:
                return "Speler";
            case 2:
                return "Egoïst";
            case 3:
                return "Mol";
            default:
                return "Onbekend";
        }
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

    // Contestant book lock
    public boolean hasBooklock() {
        return booklock;
    }

    public void setBooklock(boolean booklock) {
        this.booklock = booklock;
    }
}
