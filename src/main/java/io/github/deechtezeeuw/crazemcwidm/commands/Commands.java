package io.github.deechtezeeuw.crazemcwidm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Commands {

    public Commands() {

    }

    public abstract void onCommand(CommandSender sender, Command command, String[] args);

    public abstract String name();
    public abstract String info();
    public abstract String[] aliases();
}