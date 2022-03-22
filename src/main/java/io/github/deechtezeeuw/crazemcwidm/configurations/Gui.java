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

    // Host GUI
    public String hostTitle;
    public Integer hostSize;
    public String hostBackgroundMaterial;
    public Integer hostBackgroundAmount;
    public Short hostBackgroundShort;

    // Map GUI
    public String mapTitle;
    public Integer mapSize;
    public String mapBackgroundMaterial;
    public Integer mapBackgroundAmount;
    public Short mapBackgroundShort;

    // Game GUI
    public String gameTitle;
    public Integer gameSize;
    public String gameBackgroundMaterial;
    public Integer gameBackgroundAmount;
    public Short gameBackgroundShort;

    // Panel GUI
    public String panelTitle;
    public Integer panelSize;
    public String panelBackgroundMaterial;
    public Integer panelBackgroundAmount;
    public Short panelBackgroundShort;

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

        mapTitle = config.getString("gui.map.title");
        mapSize = config.getInt("gui.map.size");
        mapBackgroundMaterial = config.getString("gui.map.background.material");
        mapBackgroundAmount = config.getInt("gui.map.background.amount");
        temp = config.getInt("gui.map.background.short");
        mapBackgroundShort = temp.shortValue();

        gameTitle = config.getString("gui.game.title");
        gameSize = config.getInt("gui.game.size");
        gameBackgroundMaterial = config.getString("gui.game.background.material");
        gameBackgroundAmount = config.getInt("gui.game.background.amount");
        temp = config.getInt("gui.game.background.short");
        gameBackgroundShort = temp.shortValue();

        panelTitle = config.getString("gui.panel.title");
        panelSize = config.getInt("gui.panel.size");
        panelBackgroundMaterial = config.getString("gui.panel.background.material");
        panelBackgroundAmount = config.getInt("gui.panel.background.amount");
        temp = config.getInt("gui.panel.background.short");
        panelBackgroundShort = temp.shortValue();
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
