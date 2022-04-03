package io.github.deechtezeeuw.crazemcwidm.commands;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WIDM extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        Player player = (Player) sender;
        sender.sendMessage("je bent: " + (plugin.getGameDataManager().alreadyHosting(player.getUniqueId()) ? "wel" : "geen") + " host");
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
