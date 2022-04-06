package io.github.deechtezeeuw.crazemcwidm.commands.msgh;

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

import java.util.Arrays;
import java.util.UUID;

public class Msgh extends Commands {
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

        if (args.length > 0) {
            // Check if player is host
            if (game.isHost(player.getUniqueId())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan als host niet jezelf berichten!"));
                return;
            }
            Contestant contestant = game.getContestant(player.getUniqueId());

            for (UUID singlePlayer : game.getHosts()) {
                if (Bukkit.getServer().getPlayer(singlePlayer) == null) continue;
                Bukkit.getServer().getPlayer(singlePlayer).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&a&lMSGH &8>> " + contestant.getChatColor() + contestant.getPlayername() + "&8: &f" + Arrays.toString(args)));
            }
        }
    }

    @Override
    public String name() {
        return "msgh";
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
