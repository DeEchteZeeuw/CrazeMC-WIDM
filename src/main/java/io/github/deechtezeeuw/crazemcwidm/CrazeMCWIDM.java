package io.github.deechtezeeuw.crazemcwidm;

import io.github.deechtezeeuw.crazemcwidm.managers.CommandManager;
import io.github.deechtezeeuw.crazemcwidm.managers.ConfigManager;
import io.github.deechtezeeuw.crazemcwidm.managers.EventManager;
import io.github.deechtezeeuw.crazemcwidm.managers.GameManager;
import io.github.deechtezeeuw.crazemcwidm.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class CrazeMCWIDM extends JavaPlugin {
    // Instance
    private static CrazeMCWIDM instance;

    // Connection
    private MySQL SQL;

    // Managers
    private ConfigManager configManager;
    private CommandManager commandManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Instance
        instance = this;

        // Managers
        configManager = new ConfigManager();

        // Connect to db
        this.SQL = new MySQL();
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    configManager.getMain().consolePrefix + configManager.getMain().serverDivider + "&cDatabase not connected!"));
        }

        if (SQL.isConnected()) {
            SQL.sqlCreate.gameTable();
            SQL.sqlCreate.contestantTable();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    configManager.getMain().consolePrefix + configManager.getMain().serverDivider + "&aDatabase is connected!"));
        }

        // Managers after DB load
        commandManager = new CommandManager();
        commandManager.setup();
        new EventManager();

        // Game manager
        gameManager = new GameManager();

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                configManager.getMain().consolePrefix + configManager.getMain().serverDivider + configManager.getMessages().consoleOnEnable));
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                configManager.getMain().consolePrefix + configManager.getMain().serverDivider + configManager.getMessages().consoleOnDisable));
    }

    // Returns instance
    public static CrazeMCWIDM getInstance() {
        return instance;
    }

    // Returns SQL
    public MySQL getSQL() {
        return SQL;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
