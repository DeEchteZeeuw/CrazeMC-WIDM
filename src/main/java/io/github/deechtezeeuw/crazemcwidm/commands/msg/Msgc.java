package io.github.deechtezeeuw.crazemcwidm.commands.msg;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.PanelMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Msgc extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check if player
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }
        Player player = (Player) sender;

        // Get game
        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()): null;

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden waar jij in zit!"));
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld!"));
            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        if (args.length > 1) {
            // Check if player is host
            if (game.isContestant(player.getUniqueId())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan als speler niet jezelf of andere berichten!"));
                return;
            }

            // Check if name is contestant
            Contestant contestant = null;
            for (Contestant singleContestant : game.getContestant()) {
                if (singleContestant.getPlayername() == null) continue;
                if (singleContestant.getPlayername().equalsIgnoreCase(args[0])) contestant = singleContestant;
            }

            if (contestant == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cGeen speler gevonden in de game met de naam &4&l" + args[0] + "&c!"));
                return;
            }

            if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler die je wilde berichten is offline!"));
                return;
            }
            List<String> msg = Arrays.asList(args);
            msg.remove(0);

            Bukkit.getServer().getPlayer(contestant.getPlayer()).sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&a&lMSGC &8>> &b&lHost&8: " + msg.toString()));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&c/msgc <speler> <bericht>"));
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
