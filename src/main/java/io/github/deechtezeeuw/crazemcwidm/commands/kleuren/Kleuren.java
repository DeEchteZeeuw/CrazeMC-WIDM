package io.github.deechtezeeuw.crazemcwidm.commands.kleuren;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Kleuren extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }
        Player player = (Player) sender;

        Game game = (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) ? plugin.getGameDataManager().getWorldGame(player.getWorld().getUID()) : null;

        if (game == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in een game!"));
            return;
        }

        List<String> message = new ArrayList<>();

        message.add(ChatColor.translateAlternateColorCodes('&',
                "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &a&lKleuren &7&m&l]----------------"));

        int numbers = 0;
        for (Contestant contestant : game.getContestant()) {
            if (contestant.getPlayer() != null) {
                message.add(ChatColor.translateAlternateColorCodes('&',
                        contestant.getChatColor() + contestant.getColorName() + "&f: &5&l" + contestant.getPlayername() + ((contestant.getDeath()) ? "&2&l✔" : "&4&l✘") ));
                numbers++;
            }
        }

        if (numbers == 0)  message.add(ChatColor.translateAlternateColorCodes('&',
                "&l                       &c&lGeen spelers geregistreerd!"));

        message.add(ChatColor.translateAlternateColorCodes('&',
                "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &a&lKleuren &7&m&l]----------------"));

        player.sendMessage(message.toArray(new String[0]));
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
