package io.github.deechtezeeuw.crazemcwidm.commands.vote;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vote extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check if player is an real player
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) :  (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe moet in een spel zitten om gebruik te maken van vote!"));
            new PanelMenu().open(player);
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld!"));
            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        // Check if the game has started
        if (game.getGameStatus() != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen vote als de game is gestart!"));
            return;
        }

        // Check if args == 0
        if (args.length == 0) {
            // Show current game votes
            List<String> message = new ArrayList<>();
            message.add(ChatColor.translateAlternateColorCodes('&',
                    "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &a&lVotes &7&m&l]----------------"));

            // Check if there are votes registrate
            ArrayList<io.github.deechtezeeuw.crazemcwidm.classes.Vote> votes = plugin.getVoteManager().gameVotes(game.getUuid());
            if (votes.size() > 0) {
                for (io.github.deechtezeeuw.crazemcwidm.classes.Vote vote : votes) {
                    Contestant voter = game.getContestant(vote.getContestant());
                    Contestant votedOn = game.getContestant(vote.getVotedOn());
                    message.add(ChatColor.translateAlternateColorCodes('&',
                            voter.getChatColor() + voter.getPlayername() + " &fheeft gestemd op " + votedOn.getChatColor() + votedOn.getPlayername() + " &7&l(&f&l" + vote.getVotes() + "&7&l)"));
                }
            } else {
                // No votes
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&l                       &c&lGeen votes geregistreerd!"));
            }

            message.add(ChatColor.translateAlternateColorCodes('&',
                    "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &a&lVotes &7&m&l]----------------"));

            player.sendMessage(message.toArray(new String[0]));
            return;
        }

        // Check if reset
        if (args[0].equalsIgnoreCase("reset")) {
            if (!game.isHost(player.getUniqueId())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cAlleen een host mag votes resetten!"));
                return;
            }

            try {
                plugin.getVoteManager().deleteGameVotes(game.getUuid());
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                return;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de votes gereset!"));

            for (Contestant contestant : game.getContestant()) {
                if (contestant.getPlayer() == null) continue;
                if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) continue;
                if (!Bukkit.getServer().getPlayer(contestant.getPlayer()).getWorld().getUID().equals(game.getMap())) continue;
                Bukkit.getServer().getPlayer(contestant.getPlayer()).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aDe votes zijn weggepoetst! Je kan weer voten!"));
            }
            return;
        }

        if (!game.isContestant(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe mag alleen voten als speler in de game!"));
            return;
        }
        Contestant playerContestant = game.getContestant(player.getUniqueId());
        // Check if player already voted
        if (plugin.getVoteManager().hasVoted(game.getUuid(), playerContestant.getUuid())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe hebt al gevote, wacht tot de host de votes reset!"));
            return;
        }

        // Check if argument player exists
        UUID argumentPlayer = (Bukkit.getServer().getPlayer(args[0]) != null) ? Bukkit.getServer().getPlayer(args[0]).getUniqueId() : (Bukkit.getServer().getOfflinePlayer(args[0]) != null) ? Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId() : null;
        if (argumentPlayer == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler is niet bekend op de server!"));
            return;
        }

        // Check if argument player is player in game
        if (!game.isContestant(argumentPlayer)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cSpeler is geen deelnemer in dit spel!"));
            return;
        }

        Contestant argumentContestant = game.getContestant(argumentPlayer);

        io.github.deechtezeeuw.crazemcwidm.classes.Vote vote = new io.github.deechtezeeuw.crazemcwidm.classes.Vote();
        vote.setVoteID(UUID.randomUUID());
        vote.setGame(game.getUuid());
        vote.setContestant(playerContestant.getUuid());
        vote.setVotedOn(argumentContestant.getUuid());
        vote.setVotes(plugin.getVoteManager().onPlayerVotes(argumentContestant.getUuid()) + 1);

        try {
            plugin.getVoteManager().addVote(vote);
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets mis gegaan tijdens het stemmen!"));
            return;
        }

        for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
            worldPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + playerContestant.getChatColor() + playerContestant.getPlayername() + " &fheeft gestemd op " + argumentContestant.getChatColor() + argumentContestant.getPlayername() + " &7&l(&f&l" + plugin.getVoteManager().onPlayerVotes(argumentContestant.getUuid()) + "&7&l)"));
        }
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
