package io.github.deechtezeeuw.crazemcwidm.commands;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Games extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        sender.sendMessage("Er zijn op het moment: " + plugin.getSQL().sqlSelect.gameList().size() + " game(s) in de database");
    }

    @Override
    public String name() {
        return "games";
    }

    @Override
    public String info() {
        return "Shows a list of all games";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
