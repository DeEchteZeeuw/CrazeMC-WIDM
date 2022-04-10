package io.github.deechtezeeuw.crazemcwidm.commands.egowin;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.PanelMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Egowin extends Commands {
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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden waar jij in zit!"));
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld!"));
            return;
        }

        if (!game.isContestant(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen egowin gebruiken als deelnemer!"));
            return;
        }

        if (game.getGameStatus() != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen egowin gebruiken als het spel bezig is!"));
            return;
        }

        Contestant contestant = game.getContestant(player.getUniqueId());

        if (!contestant.getRoleName().equalsIgnoreCase("egoïst")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen finishen als ego met egowin!"));
            return;
        }

        if (contestant.getDeath()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&CJe kan alleen finishen als je levend bent!"));
            return;
        }

        boolean inRange = false;
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    Block block = player.getLocation().getBlock().getRelative(x, y, z);
                    if (block.getType().equals(Material.ENDER_STONE)) inRange = true;
                }
            }
        }

        if (!inRange) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe moet in minimaal 3 blokken bereik staat van de endstone!"));
            return;
        }

        game.setGameStatus(3);
        for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
            worldPlayer.sendTitle(
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                    ChatColor.translateAlternateColorCodes('&', "&bHet spel is gewonnen door de &7&lEgoïsten&b!"),
                    1, 60, 1);
        }

        List<String> message = new ArrayList<>();

        message.add(ChatColor.translateAlternateColorCodes('&',
                "&7&m&l---------[ " + plugin.getConfigManager().getMain().serverPrefix + " &c&lAfgelopen &7&m&l]---------"));

        String Players = "";
        String Egoists = "";
        String Mollen = "";
        String Peacekeepers = "";

        for(Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            // If player is player
            if (singleContestant.getRole() == 1) {
                if (Players.length() == 0) {
                    Players += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                } else {
                    Players += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                }
            }
            // If player is egoist
            if (singleContestant.getRole() == 2) {
                if (Egoists.length() == 0) {
                    Egoists += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                } else {
                    Egoists += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                }
            }
            // If player is mol
            if (singleContestant.getRole() == 3) {
                if (Mollen.length() == 0) {
                    Mollen += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                } else {
                    Mollen += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                }
            }
            // If player is peacekeeper
            if (singleContestant.getPeacekeeper()) {
                if (Peacekeepers.length() == 0) {
                    Peacekeepers += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                } else {
                    Peacekeepers += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                }
            }
        }

        // Spelers
        message.add(ChatColor.translateAlternateColorCodes('&',
                "&5&lSpelers: &f" + Players));
        // Egoïst
        message.add(ChatColor.translateAlternateColorCodes('&',
                "&7&lEgoïsten: &f" + Egoists));
        // Mollen
        message.add(ChatColor.translateAlternateColorCodes('&',
                "&b&lMollen: &f" + Mollen));
        // Peacekeeper
        message.add(ChatColor.translateAlternateColorCodes('&',
                "&a&lPeacekeeper: &f" + Peacekeepers));
        // Finisher
        message.add(ChatColor.translateAlternateColorCodes('&',
                "&dFinisher: "+contestant.getChatColor()+"✤ &c"+contestant.getPlayername()));

        message.add(ChatColor.translateAlternateColorCodes('&',
                "&7&m&l---------[ " + plugin.getConfigManager().getMain().serverPrefix + " &c&lAfgelopen &7&m&l]---------"));

        for (Player singlePlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
            singlePlayer.sendMessage(message.toArray(new String[0]));

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    singlePlayer.teleport(Bukkit.getServer().getWorld("WIDM-Lobby").getSpawnLocation());
                }
            }, 200L);
        }
    }

    @Override
    public String name() {
        return "egowin";
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
