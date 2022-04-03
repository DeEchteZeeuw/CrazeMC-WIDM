package io.github.deechtezeeuw.crazemcwidm.commands.vote;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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

        if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe zit niet in een game wereld!"));
            return;
        }

        Game game = plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID());

        if (game == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe moet in een game zitten hiervoor!"));
            return;
        }

        // Check if its the good world
        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de juiste wereld!"));
            return;
        }

        // Check if the game has started
        if (game.getGameStatus() != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen vote als de game is gestart!"));
            return;
        }

        if (args.length == 0) {
            ArrayList<io.github.deechtezeeuw.crazemcwidm.classes.Vote> votes = plugin.getGameManager().getGameVotes(game.getUuid());

            ArrayList<String> sendMessages = new ArrayList<>();
            sendMessages.add(ChatColor.translateAlternateColorCodes('&',
                    "&7&m&l----------[" + plugin.getConfigManager().getMain().serverPrefix + " &a&lVotes &7&m&l]----------"));

            for (io.github.deechtezeeuw.crazemcwidm.classes.Vote vote : votes) {
                if (vote.getGame() == null || vote.getContestant() == null || vote.getVotedOn() == null) continue;
                Contestant voter = null;
                Contestant votedOn = null;

                for (Contestant singleContestant : game.getContestant()) {
                    if (vote.getContestant().equals(singleContestant.getUuid())) voter = singleContestant;
                    if (vote.getVotedOn().equals(singleContestant.getUuid())) votedOn = singleContestant;
                }

                if (voter == null || votedOn == null) return;
                sendMessages.add(ChatColor.translateAlternateColorCodes('&',
                        voter.getChatColor() + voter.getPlayername() + " &fheeft gevote op " + votedOn.getChatColor() + votedOn.getPlayername() + " &7&l(&f&l" + plugin.getGameManager().getVotesOnPlayer(game.getUuid(), votedOn.getUuid()) + "&7&l)"));
            }

            if (sendMessages.size() == 1) {
                sendMessages.add(ChatColor.translateAlternateColorCodes('&',
                        "&c&lGeen votes geregistreerd!"));
            }

            sendMessages.add(ChatColor.translateAlternateColorCodes('&',
                    "&7&m&l----------[" + plugin.getConfigManager().getMain().serverPrefix + " &a&lVotes &7&m&l]----------"));

            player.sendMessage(sendMessages.toArray(new String[0]));
            return;
        }

        // if vote reset and is host
        if (args[0].equalsIgnoreCase("reset") && game.isHost(player.getUniqueId())) {
            try {
                plugin.getGameManager().resetGameVotes(game.getUuid());
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                return;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de votes gereset!"));
            return;
        }

        Contestant contestant = plugin.getSQL().sqlSelect.getPlayerContestant(player.getUniqueId());
        if (contestant == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe moet hiervoor speler zijn in dit spel!"));
            return;
        }

        // Check if player has voted already
        if (plugin.getGameManager().hasVoted(game.getUuid(), contestant.getUuid())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe hebt al gevote, wacht tot de host de votes reset!"));
            return;
        }

        // Check if player exists
        String playername = args[0];

        if (player.getName().equalsIgnoreCase(playername)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan niet op jezelf stemmen!"));
            return;
        }

        Contestant votedOn = null;
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null || singleContestant.getDeath()) continue;
            if (singleContestant.getPlayername().equalsIgnoreCase(playername)) votedOn = singleContestant;
        }

        if (votedOn == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe speler die jij koos bestaat niet in dit spel of is dood op het moment!"));
            return;
        }

        try {
            io.github.deechtezeeuw.crazemcwidm.classes.Vote vote = new io.github.deechtezeeuw.crazemcwidm.classes.Vote();
            vote.setVoteID(UUID.randomUUID());
            vote.setGame(game.getUuid());
            vote.setContestant(contestant.getUuid());
            vote.setVotedOn(votedOn.getUuid());

            plugin.getGameManager().setGameVote(vote);
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr ging iets mis, neem contact op met de host!"));
            return;
        }

        for (Player singlePlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
            singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + contestant.getChatColor() + contestant.getPlayername() + " &fheeft op " + votedOn.getChatColor() + votedOn.getPlayername() + " &fgevote &7&l(&f&l" + plugin.getGameManager().getVotesOnPlayer(game.getUuid(), votedOn.getUuid()) + "&7&l)"));
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
