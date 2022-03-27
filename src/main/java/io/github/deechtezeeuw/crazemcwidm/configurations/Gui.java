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

    // Items GUI
    public String itemsTitle;
    public Integer itemsSize;
    public String itemsBackgroundMaterial;
    public Integer itemsBackgroundAmount;
    public Short itemsBackgroundShort;

    // Enchanted Gear GUI
    public String enchantedGearTitle;
    public Integer enchantedGearSize;
    public String enchantedGearBackgroundMaterial;
    public Integer enchantedGearBackgroundAmount;
    public Short enchantedGearBackgroundShort;

    // Non Enchanted Gear GUI
    public String nonEnchantedGearTitle;
    public Integer nonEnchantedGearSize;
    public String nonEnchantedGearBackgroundMaterial;
    public Integer nonEnchantedGearBackgroundAmount;
    public Short nonEnchantedGearBackgroundShort;

    // Enchanted Leather Gear GUI
    public String enchantedLeatherGearTitle;
    public Integer enchantedLeatherGearSize;
    public String enchantedLeatherGearBackgroundMaterial;
    public Integer enchantedLeatherGearBackgroundAmount;
    public Short enchantedLeatherGearBackgroundShort;

    // Non Enchanted Leather Gear GUI
    public String nonEnchantedLeatherGearTitle;
    public Integer nonEnchantedLeatherGearSize;
    public String nonEnchantedLeatherGearBackgroundMaterial;
    public Integer nonEnchantedLeatherGearBackgroundAmount;
    public Short nonEnchantedLeatherGearBackgroundShort;

    // Weapons GUI
    public String weaponsTitle;
    public Integer weaponsSize;
    public String weaponsBackgroundMaterial;
    public Integer weaponsBackgroundAmount;
    public Short weaponsBackgroundShort;

    // Swords GUI
    public String swordsTitle;
    public Integer swordsSize;
    public String swordsBackgroundMaterial;
    public Integer swordsBackgroundAmount;
    public Short swordsBackgroundShort;

    // Bows GUI
    public String bowsTitle;
    public Integer bowsSize;
    public String bowsBackgroundMaterial;
    public Integer bowsBackgroundAmount;
    public Short bowsBackgroundShort;

    // Others GUI
    public String othersTitle;
    public Integer othersSize;
    public String othersBackgroundMaterial;
    public Integer othersBackgroundAmount;
    public Short othersBackgroundShort;

    // Hosts GUI
    public String hostsTitle;
    public String hostsBackgroundMaterial;
    public Integer hostsBackgroundAmount;
    public Short hostsBackgroundShort;

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

        itemsTitle = config.getString("gui.items.title");
        itemsSize = config.getInt("gui.items.size");
        itemsBackgroundMaterial = config.getString("gui.items.background.material");
        itemsBackgroundAmount = config.getInt("gui.items.background.amount");
        temp = config.getInt("gui.items.background.short");
        itemsBackgroundShort = temp.shortValue();

        enchantedGearTitle = config.getString("gui.enchantedGear.title");
        enchantedGearSize = config.getInt("gui.enchantedGear.size");
        enchantedGearBackgroundMaterial = config.getString("gui.enchantedGear.background.material");
        enchantedGearBackgroundAmount = config.getInt("gui.enchantedGear.background.amount");
        temp = config.getInt("gui.enchantedGear.background.short");
        enchantedGearBackgroundShort = temp.shortValue();

        nonEnchantedGearTitle = config.getString("gui.nonEnchantedGear.title");
        nonEnchantedGearSize = config.getInt("gui.nonEnchantedGear.size");
        nonEnchantedGearBackgroundMaterial = config.getString("gui.nonEnchantedGear.background.material");
        nonEnchantedGearBackgroundAmount = config.getInt("gui.nonEnchantedGear.background.amount");
        temp = config.getInt("gui.nonEnchantedGear.background.short");
        nonEnchantedGearBackgroundShort = temp.shortValue();

        enchantedLeatherGearTitle = config.getString("gui.leatherGear.title");
        enchantedLeatherGearSize = config.getInt("gui.leatherGear.size");
        enchantedLeatherGearBackgroundMaterial = config.getString("gui.leatherGear.background.material");
        enchantedLeatherGearBackgroundAmount = config.getInt("gui.leatherGear.background.amount");
        temp = config.getInt("gui.leatherGear.background.short");
        enchantedLeatherGearBackgroundShort = temp.shortValue();

        nonEnchantedLeatherGearTitle = config.getString("gui.nonLeatherGear.title");
        nonEnchantedLeatherGearSize = config.getInt("gui.nonLeatherGear.size");
        nonEnchantedLeatherGearBackgroundMaterial = config.getString("gui.nonLeatherGear.background.material");
        nonEnchantedLeatherGearBackgroundAmount = config.getInt("gui.nonLeatherGear.background.amount");
        temp = config.getInt("gui.nonLeatherGear.background.short");
        nonEnchantedLeatherGearBackgroundShort = temp.shortValue();

        weaponsTitle = config.getString("gui.weapons.title");
        weaponsSize = config.getInt("gui.weapons.size");
        weaponsBackgroundMaterial = config.getString("gui.weapons.background.material");
        weaponsBackgroundAmount = config.getInt("gui.weapons.background.amount");
        temp = config.getInt("gui.weapons.background.short");
        weaponsBackgroundShort = temp.shortValue();

        swordsTitle = config.getString("gui.swords.title");
        swordsSize = config.getInt("gui.swords.size");
        swordsBackgroundMaterial = config.getString("gui.swords.background.material");
        swordsBackgroundAmount = config.getInt("gui.swords.background.amount");
        temp = config.getInt("gui.swords.background.short");
        swordsBackgroundShort = temp.shortValue();

        bowsTitle = config.getString("gui.bows.title");
        bowsSize = config.getInt("gui.bows.size");
        bowsBackgroundMaterial = config.getString("gui.bows.background.material");
        bowsBackgroundAmount = config.getInt("gui.bows.background.amount");
        temp = config.getInt("gui.bows.background.short");
        bowsBackgroundShort = temp.shortValue();

        othersTitle = config.getString("gui.others.title");
        othersSize = config.getInt("gui.others.size");
        othersBackgroundMaterial = config.getString("gui.others.background.material");
        othersBackgroundAmount = config.getInt("gui.others.background.amount");
        temp = config.getInt("gui.others.background.short");
        othersBackgroundShort = temp.shortValue();

        hostsTitle = config.getString("gui.hosts.title");
        hostsBackgroundMaterial = config.getString("gui.hosts.background.material");
        hostsBackgroundAmount = config.getInt("gui.hosts.background.amount");
        temp = config.getInt("gui.hosts.background.short");
        hostsBackgroundShort = temp.shortValue();
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
