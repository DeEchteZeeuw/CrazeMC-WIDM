package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.events.InventoryClick;
import io.github.deechtezeeuw.crazemcwidm.events.PlayerChangedWorld;
import io.github.deechtezeeuw.crazemcwidm.events.PlayerJoin;
import io.github.deechtezeeuw.crazemcwidm.events.PlayerMove;

public class EventManager {

    public EventManager() {
        CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerChangedWorld(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMove(), plugin);
    }
}
