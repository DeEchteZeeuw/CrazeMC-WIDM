package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.events.InventoryClick;

public class EventManager {

    public EventManager() {
        CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
    }
}
