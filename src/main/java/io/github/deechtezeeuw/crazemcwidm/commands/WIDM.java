package io.github.deechtezeeuw.crazemcwidm.commands;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WIDM extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        sender.sendMessage("er zijn: " + plugin.getGameDataManager().getGameHashMap().size() + " games");
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
