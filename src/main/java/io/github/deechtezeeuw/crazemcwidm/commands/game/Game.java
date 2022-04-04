package io.github.deechtezeeuw.crazemcwidm.commands.game;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.GameMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Game extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check if user is in-game
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

        if (!plugin.getGameDataManager().alreadyHosting(player.getUniqueId()) && !plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe zit niet in een game!"));
            return;
        }

        // Check if there are no arguments
        if (args.length <= 0) {
            new GameMenu().open(player);
            return;
        }

        // Greater then
        if (args.length > 0) {
            if (!sender.hasPermission("crazemc.tempvuller") && !sender.hasPermission("crazemc.jrgamehost") && !sender.hasPermission("crazemc.gamehost") && !sender.hasPermission("crazemc.srgamehost")) {
                plugin.getCommandManager().noPermission(null, sender);
                return;
            }
            // Check if player is an host
            if (!plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
                return;
            }
            io.github.deechtezeeuw.crazemcwidm.classes.Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());
            // Check if right world
            if (!player.getWorld().getUID().equals(game.getMap())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de juiste wereld hiervoor!"));
                return;
            }
            // Add player to game
            if (args[0].equalsIgnoreCase("add")) {
                // Check enough args
                if (args.length == 3) {
                    new GameAdd().onCommand(sender, command, args);
                    return;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cBedoel je &4&l/game add <color> <player>"));
                return;
            }

            // Delete color player
            if (args[0].equalsIgnoreCase("remove")) {
                // Check enough args
                if (args.length == 2) {
                    new GameRemove().onCommand(sender, command, args);
                    return;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cBedoel je &4&l/game remove <color>"));
                return;
            }
        }
    }

    @Override
    public String name() {
        return "game";
    }

    @Override
    public String info() {
        return "See all information about your game";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
