package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.configurations.Gui;
import io.github.deechtezeeuw.crazemcwidm.configurations.Main;
import io.github.deechtezeeuw.crazemcwidm.configurations.Messages;

public class ConfigManager {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private Main main = new Main();
    private Messages messages = new Messages();
    private Gui gui = new Gui();

    public Main getMain() {
        return main;
    }

    public Messages getMessages() {
        return messages;
    }

    public Gui getGui() {
        return gui;
    }
}
