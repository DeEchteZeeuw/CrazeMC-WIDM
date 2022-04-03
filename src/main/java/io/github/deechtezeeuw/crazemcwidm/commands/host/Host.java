package io.github.deechtezeeuw.crazemcwidm.commands.host;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.HostMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Host extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("crazemc.host")) {
            plugin.getCommandManager().noPermission(null, sender);
            return;
        }

        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

        // Check if player is already hosting an game
        if (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cUnclaim eerst je huidige game via /unclaim of /panel!"));
            return;
        }

        new HostMenu().open(player);
    }

    @Override
    public String name() {
        return "host";
    }

    @Override
    public String info() {
        return "Main command of host";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
