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

public class GameRemove extends Commands {
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
        Contestant contestant = null;

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

        if (contestant.getPlayer() == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cKleur is niet bezet!"));
            return;
        }

        UUID removedPlayer = contestant.getPlayer();

        try {
            // Remove player in color
            contestant.setPlayer(null);
            // Update in database
            game.updateContestant(contestant);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        // Send player that he has been removed from the game
        if (Bukkit.getServer().getPlayer(removedPlayer) != null) {
            Bukkit.getServer().getPlayer(removedPlayer).sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent verwijderd uit het spel door de host!"));
        }

        // Send succes message to host
        for (UUID host : game.getHosts()) {
            if (Bukkit.getServer().getPlayer(host) != null) {
                Bukkit.getServer().getPlayer(host).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de speler van kleur " + contestant.getChatColor() + contestant.getColorName() + " &averwijderd!"));
            }
        }

        // Teleport player lobby
        if (Bukkit.getServer().getPlayer(removedPlayer) != null) {
            Bukkit.getServer().getPlayer(removedPlayer).teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        }

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
