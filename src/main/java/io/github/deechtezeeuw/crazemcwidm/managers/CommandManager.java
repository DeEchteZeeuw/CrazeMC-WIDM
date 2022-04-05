package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.commands.WIDM;
import io.github.deechtezeeuw.crazemcwidm.commands.color.Color;
import io.github.deechtezeeuw.crazemcwidm.commands.game.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.host.Host;
import io.github.deechtezeeuw.crazemcwidm.commands.itemlock.ItemLock;
import io.github.deechtezeeuw.crazemcwidm.commands.itemlock.ItemUndroppable;
import io.github.deechtezeeuw.crazemcwidm.commands.items.Items;
import io.github.deechtezeeuw.crazemcwidm.commands.kleuren.Kleuren;
import io.github.deechtezeeuw.crazemcwidm.commands.panel.Panel;
import io.github.deechtezeeuw.crazemcwidm.commands.queue.Queue;
import io.github.deechtezeeuw.crazemcwidm.commands.unclaim.Unclaim;
import io.github.deechtezeeuw.crazemcwidm.commands.vote.Vote;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private final ArrayList<Commands> commands = new ArrayList<>();

    // Main command
    public String main = "widm";
    public String host = "host";
    public String game = "game";
    public String panel = "panel";
    public String queue = "queue";
    public String items = "items";
    public String itemlock = "itemlock";
    public String undroppable = "undroppable";
    public String unclaim = "unclaim";
    public String vote = "vote";
    public String kleuren = "kleuren";

    public void setup() {
        plugin.getCommand(main).setExecutor(this);
        plugin.getCommand(host).setExecutor(this);
        plugin.getCommand(game).setExecutor(this);
        plugin.getCommand(panel).setExecutor(this);
        plugin.getCommand(queue).setExecutor(this);
        plugin.getCommand(items).setExecutor(this);
        plugin.getCommand(itemlock).setExecutor(this);
        plugin.getCommand(undroppable).setExecutor(this);
        plugin.getCommand(unclaim).setExecutor(this);
        plugin.getCommand(vote).setExecutor(this);
        plugin.getCommand(kleuren).setExecutor(this);

        for (String color : plugin.getGameManager().getColors()) {
            plugin.getCommand(color).setExecutor(this);
        }

//        plugin.getCommand(main).setTabCompleter(new tabComplEvent());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Host command
        if (command.getName().equalsIgnoreCase(host)) {
            new Host().onCommand(sender, command, args);
            return true;
        }
        // Game command
        if (command.getName().equalsIgnoreCase(game)) {
            new Game().onCommand(sender, command, args);
            return true;
        }
        // Panel command
        if (command.getName().equalsIgnoreCase(panel)) {
            new Panel().onCommand(sender, command, args);
            return true;
        }

        // Color commands
        for (String color : plugin.getGameManager().getColors()) {
            if (command.getName().equalsIgnoreCase(color)) {
                new Color().onCommand(sender, command, args);
                return true;
            }
        }

        // Queue commands
        if (command.getName().equalsIgnoreCase(queue)) {
            new Queue().onCommand(sender, command, args);
            return true;
        }

        // Items commands
        if (command.getName().equalsIgnoreCase(items)) {
            new Items().onCommand(sender, command, args);
            return true;
        }

        // Itemlock
        if (command.getName().equalsIgnoreCase(itemlock)) {
            new ItemLock().onCommand(sender, command, args);
            return true;
        }

        // Undroppable
        if (command.getName().equalsIgnoreCase(undroppable)) {
            new ItemUndroppable().onCommand(sender, command, args);
            return true;
        }

        // Unclaim
        if (command.getName().equalsIgnoreCase(unclaim)) {
            new Unclaim().onCommand(sender, command, args);
            return true;
        }

        // Vote
        if (command.getName().equalsIgnoreCase(vote)) {
            new Vote().onCommand(sender, command, args);
            return true;
        }

        // Kleuren
        if (command.getName().equalsIgnoreCase(kleuren)) {
            new Kleuren().onCommand(sender, command, args);
            return true;
        }

        // Widm
        if (command.getName().equalsIgnoreCase(main)) {
            new WIDM().onCommand(sender, command, args);
            return true;
        }

        if (!(command.getName().equalsIgnoreCase(main))) return true;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getMessages().generalNoArguments));
            return true;
        }

        Commands target = this.get(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getMessages().generalInvalidSubcommand));
            return true;
        }

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
        arrayList.remove(0);

        try {
            target.onCommand(sender, command, args);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error has occurred");
            e.printStackTrace();
        }

        return true;
    }

    private Commands get(String name) {

        for (Commands sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int x = 0; x < length; ++x) {
                String alias = aliases[x];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }

    public void noPermission(Player player, CommandSender sender) {
        if (player != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getMessages().generalNoPermission));
        }
        if (sender != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getMessages().generalNoPermission));
        }
    }

    public void onlyPlayer(CommandSender sender) {
        if (sender == null) return;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getMessages().generalNotIngame));
    }
}
