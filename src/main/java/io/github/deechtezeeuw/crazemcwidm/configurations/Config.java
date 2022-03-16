package io.github.deechtezeeuw.crazemcwidm.configurations;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public abstract class Config {

    private File configFile;
    private FileConfiguration config;

    public abstract FileConfiguration getConfig();
    public abstract void createConfig();
    public abstract void load();
    public abstract void reload();
}
