package io.github.deechtezeeuw.crazemcwidm.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Game {
    protected UUID uuid; // Special id of the game
    protected UUID map; // Special id of the world its playing in
    protected ArrayList<UUID> hosts = new ArrayList<>(); // Arraylist of the host
    protected String theme = ""; // String for the theme map
    protected Integer gameStatus;

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

    protected ItemStack Item(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }
}
