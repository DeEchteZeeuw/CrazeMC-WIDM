package io.github.deechtezeeuw.crazemcwidm.commands.game;

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

import java.util.UUID;

public class GameAdd extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check permission
        if (!sender.hasPermission("crazemc.tempvuller") && !sender.hasPermission("crazemc.jrgamehost") && !sender.hasPermission("crazemc.gamehost") && !sender.hasPermission("crazemc.srgamehost")) {
            plugin.getCommandManager().noPermission(null ,sender);
        }
        Player player = (Player)sender;
        // Check if host
        if (!plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
            return;
        }

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) :  null;

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden die jij host, het menu sluit!"));
            new PanelMenu().open(player);
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld, het menu sluit!"));
            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        String Color = args[1].toLowerCase();
        String Playername = args[2];
        Contestant contestant = null;

        // Player checks
        if (Bukkit.getServer().getPlayer(Playername) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler is niet online!"));
            return;
        }
        Player arg2Player = Bukkit.getServer().getPlayer(Playername);

        // Check if player is in queue
        if (!plugin.getGameManager().getQueue().contains(arg2Player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler zit niet in de queue!"));
            return;
        }

        // Check if player is not in a game
        if (plugin.getGameDataManager().alreadyHosting(arg2Player.getUniqueId()) || plugin.getGameDataManager().alreadyContestant(arg2Player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler zit in een game!"));
            return;
        }

        // Color checks
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getColorName().toLowerCase().equalsIgnoreCase(Color)) {
                contestant = singleContestant;
            }
        }

        if (contestant == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cKleur bestaat niet in het spel!"));
            return;
        }

        if (contestant.getPlayer() != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cKleur is al bezet!"));
            return;
        }

        try {
            // Remove player from queue
            plugin.getGameManager().getQueue().remove(arg2Player.getUniqueId());
            // Set player in color
            contestant.setPlayer(arg2Player.getUniqueId());
            // Update
            game.updateContestant(contestant);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        // Send player that he has been added to the game
        arg2Player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aJe bent toegevoegd aan een WIDM game! Je wordt zo geteleporteerd!"));

        // Send succes message to host
        for (UUID host : game.getHosts()) {
            if (Bukkit.getServer().getPlayer(host) != null) {
                Bukkit.getServer().getPlayer(host).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol &2&l" + Playername + " &atoegevoegd aan de kleur " + contestant.getChatColor() + contestant.getColorName() + "&a!"));
            }
        }

        // Teleport player to his spawn
        if (contestant.getSpawn() != null) {
            arg2Player.teleport(contestant.getSpawn());
        } else {
            arg2Player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe spawn van jouw kleur is nog niet neergezet, neem contact op met de host of bekijk /game!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cVergeet je niet de spawn van de kleur " + contestant.getChatColor() + contestant.getColorName() + " &ate zetten? De speler is niet geteleporteerd nu!"));
        }
    }

    @Override
    public String name() {
        return "game add";
    }

    @Override
    public String info() {
        return "add someone to the game";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
