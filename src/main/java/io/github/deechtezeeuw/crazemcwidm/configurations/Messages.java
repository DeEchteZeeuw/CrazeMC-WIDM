package io.github.deechtezeeuw.crazemcwidm.configurations;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Messages extends Config {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private File configFile;
    private FileConfiguration config;

    // Variables
    public String consoleOnEnable;
    public String consoleOnDisable;

    public String generalNoArguments;
    public String generalInvalidSubcommand;
    public String generalNoPermission;
    public String generalNotIngame;
    public String generalPlayerOffline;

    public Messages() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        config= new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        this.consoleOnEnable = config.getString("console.enable");
        this.consoleOnDisable = config.getString("console.disable");

        this.generalNoArguments = config.getString("general.noArguments");
        this.generalInvalidSubcommand = config.getString("general.invalidSubcommand");
        this.generalNoPermission = config.getString("general.noPermissions");
        this.generalNotIngame = config.getString("general.notInLauncher");
        this.generalPlayerOffline = config.getString("general.playerOffline");
    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), "messages.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("messages.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }
}
