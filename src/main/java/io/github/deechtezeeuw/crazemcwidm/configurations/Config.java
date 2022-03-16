package io.github.deechtezeeuw.crazemcwidm.configurations;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class Config {

    public abstract FileConfiguration getConfig();
    public abstract void createConfig();
    public abstract void load();
    public abstract void reload();
}
