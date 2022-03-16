package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.configurations.Gui;
import io.github.deechtezeeuw.crazemcwidm.configurations.Main;
import io.github.deechtezeeuw.crazemcwidm.configurations.Messages;

public class ConfigManager {
    private final Main main = new Main();
    private final Messages messages = new Messages();
    private final Gui gui = new Gui();

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
