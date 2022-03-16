package io.github.deechtezeeuw.crazemcwidm.configurations;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Gui extends Config {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    private File configFile;
    private FileConfiguration config;

    public String hostTitle;
    public Integer hostSize;
    public String hostBackgroundMaterial;
    public Integer hostBackgroundAmount;
    public Short hostBackgroundShort;

    public Gui() {
        this.createConfig();
        this.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public void createConfig() {
        configFile = new File(plugin.getDataFolder(), "gui.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("gui.yml", false);
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
        hostTitle = config.getString("gui.host.title");
        hostSize = config.getInt("gui.host.size");
        hostBackgroundMaterial = config.getString("gui.host.background.material");
        hostBackgroundAmount = config.getInt("gui.host.background.amount");
        Integer temp = config.getInt("gui.host.background.short");
        hostBackgroundShort = temp.shortValue();
    }

    @Override
    public void reload() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), "config.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
        load();
    }
}
