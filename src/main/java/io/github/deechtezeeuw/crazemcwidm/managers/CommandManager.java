package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.commands.host.Host;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CommandManager implements CommandExecutor {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private final ArrayList<Commands> commands = new ArrayList<>();

    // Main command
    public String main = "widm";
    public String host = "host";

    public void setup() {
        plugin.getCommand(main).setExecutor(this);
        plugin.getCommand(host).setExecutor(this);

//        plugin.getCommand(main).setTabCompleter(new tabComplEvent());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Host command
        if (command.getName().equalsIgnoreCase(host)) {
            new Host().onCommand(sender, command, args);
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

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(args));
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
        Iterator<Commands> subCommands = this.commands.iterator();

        while(subCommands.hasNext()) {
            Commands sc = (Commands) subCommands.next();

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
