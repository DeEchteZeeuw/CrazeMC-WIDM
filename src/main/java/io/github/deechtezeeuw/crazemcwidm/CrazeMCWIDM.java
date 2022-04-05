package io.github.deechtezeeuw.crazemcwidm;

import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.managers.*;
import io.github.deechtezeeuw.crazemcwidm.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CrazeMCWIDM extends JavaPlugin {
    // Instance
    private static CrazeMCWIDM instance;

    // Connection
    private MySQL SQL;

    // Managers
    private ConfigManager configManager;
    private CommandManager commandManager;
    private GameManager gameManager;
    private GameDataManager gameDataManager;
    private VoteManager voteManager;

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
        // Game manager
        gameManager = new GameManager();
        commandManager = new CommandManager();
        commandManager.setup();
        gameDataManager = new GameDataManager();
        voteManager = new VoteManager();
        new EventManager();

        // Load worlds from config
        for (String world : this.getConfigManager().getMain().getConfig().getConfigurationSection("mappen").getKeys(false)) {
            String command = "mv load " + this.getConfigManager().getMain().getConfig().getString("mappen." + world + ".world");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        // Push all games from database into game
        for (Game game : this.SQL.sqlSelect.gameList()) {
            if (!this.gameManager.getGamesThatStarted().containsKey(game.getUuid())) {
                this.gameManager.setGamesThatStarted(game.getUuid());
            }
            this.getGameManager().getGamesThatStarted().put(game.getUuid(), game.getTime());
            game.updateTimer();

            // Load all worlds
            String command = "mv load " + game.getTheme().substring(0, 1).toUpperCase() + game.getTheme().substring(1) + "-" + game.getUuid();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        // New GameDataManager
        this.gameDataManager.setGameHashMap(this.SQL.sqlSelect.gameList());

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                configManager.getMain().consolePrefix + configManager.getMain().serverDivider + configManager.getMessages().consoleOnEnable));

        // Update database every 5 minutes
        new BukkitRunnable() {
            @Override
            public void run() {
                gameDataManager.updateDatabase(false);
            }
        }.runTaskTimer(this, 1, 300*20L);
    }

    @Override
    public void onDisable() {
        // push all times to the database
        for(Map.Entry<UUID, Integer> entry : this.gameManager.getGamesThatStarted().entrySet()) {
            UUID key = entry.getKey();
            Integer value = entry.getValue();

            if (this.getSQL().sqlSelect.gameExists(key)) {
                Game game = this.getSQL().sqlSelect.uuidGame(key);
                if (game.getGameStatus() == 1) game.setGameStatus(2);
                game.setTime(value);
                this.SQL.sqlUpdate.updateGame(game, "gamestatus");
                this.SQL.sqlUpdate.updateGame(game, "gametime");
            }
        }
        World world = (Bukkit.getServer().getWorld("WIDM-Lobby") == null) ? Bukkit.getServer().getWorlds().get(0) : Bukkit.getServer().getWorld("WIDM-Lobby");
        // Teleport all players to lobby
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

            player.teleport(world.getSpawnLocation());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.getConfigManager().getMain().serverPrefix + this.getConfigManager().getMain().serverDivider + "&cIn verband met een plugin reload ben je naar de lobby getelporteerd!"));
        }

        // New GameDataManger functions
        this.gameDataManager.updateDatabase(true);

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

    public GameDataManager getGameDataManager() {
        return gameDataManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }
}
