package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.events.*;

public class EventManager {

    public EventManager() {
        CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerChangedWorld(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMove(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInteract(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CraftItem(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ASyncPlayerChat(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDropItem(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeath(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), plugin);
    }
}
