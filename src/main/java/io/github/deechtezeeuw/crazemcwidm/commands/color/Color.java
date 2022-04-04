package io.github.deechtezeeuw.crazemcwidm.commands.color;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.PanelMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Color extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("crazemc.host")) {
            plugin.getCommandManager().noPermission(null, sender);
            return;
        }

        // Check if player
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

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

        Contestant contestant = null;
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getColorName() != null) {
                if (singleContestant.getColorName().toLowerCase().equalsIgnoreCase(command.getName())) contestant = singleContestant;
            }
        }

        // Check if contestant is found
        if (contestant == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cKleur zit niet in deze game. Kijk in /panel welke kleuren er zijn."));
            return;
        }

        try {
            contestant.setSpawn(player.getLocation());
            game.updateContestant(contestant);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is een fout opgetreden!"));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de spawn neergezet voor " + contestant.getChatColor() + contestant.getColorName()));
    }

    @Override
    public String name() {
        return "color";
    }

    @Override
    public String info() {
        return "Set the spawn of an specific color";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
