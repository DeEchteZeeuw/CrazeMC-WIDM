package io.github.deechtezeeuw.crazemcwidm.commands.items;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import io.github.deechtezeeuw.crazemcwidm.gui.ItemsMenu;
import io.github.deechtezeeuw.crazemcwidm.gui.PanelMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Items extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check if user has host perms
        if (!sender.hasPermission("crazemc.tempvuller") && !sender.hasPermission("crazemc.jrgamehost") && !sender.hasPermission("crazemc.gamehost") && !sender.hasPermission("crazemc.srgamehost")) {
            plugin.getCommandManager().noPermission(null, sender);
            return;
        }
        // Check if user is in-game
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

        if (!plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
            return;
        }

        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        if (!player.getWorld().getUID().equals(game.getMap())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de juiste game om dit te doen."));
            return;
        }

        // Check if there are no arguments
        if (args.length <= 0) {
            new ItemsMenu().open(player);
            return;
        }
    }

    @Override
    public String name() {
        return "items";
    }

    @Override
    public String info() {
        return "shows you the items GUI if your an host";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
