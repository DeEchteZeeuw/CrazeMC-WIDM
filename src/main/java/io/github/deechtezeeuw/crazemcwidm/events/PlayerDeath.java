package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player deadPlayer = e.getEntity();
        Location deathLocation = deadPlayer.getLocation();
        Player killer = (e.getEntity().getKiller() != null) ? e.getEntity().getKiller() : null;
        World world = deadPlayer.getWorld();

        // Check if world is a game
        if (plugin.getGameDataManager().worldIsPartOfGame(world.getUID())) {
            Game game = plugin.getGameDataManager().getWorldGame(world.getUID());
            // Check if game is busy
            if (game.getGameStatus() == 1) {
                deadPlayer.setGameMode(GameMode.SPECTATOR);

                deadPlayer.spigot().respawn();
                deadPlayer.teleport(deathLocation);

                Contestant deadPlayerWasContestant = null;
                Contestant killerWasContestant = null;
                // Check if player was contestant
                for (Contestant contestant : game.getContestant()) {
                    if (contestant.getPlayer() != null) {
                        if (contestant.getPlayer().equals(deadPlayer.getUniqueId())) deadPlayerWasContestant = contestant;
                        if (killer != null) {
                            if (contestant.getPlayer().equals(killer.getUniqueId())) killerWasContestant = contestant;
                        }
                    }
                }

                // if deadplayer and killer were contestants
                if (deadPlayerWasContestant != null && killerWasContestant != null) {
                    deadPlayerWasContestant.setDeath(true);

                    if ((killerWasContestant.getPeacekeeper())) {
                        killerWasContestant.setPeacekeeperKills(killerWasContestant.getPeacekeeperKills() + 1);

                        if (killerWasContestant.getPeacekeeperKills() >= 2) {
                            killerWasContestant.setDeath(true);
                            killer.setGameMode(GameMode.SPECTATOR);
                        }
                    } else {
                        killerWasContestant.setKills(killerWasContestant.getKills() + 1);
                    }

                    try {
                        game.updateContestant(killerWasContestant);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                    for (Player player : world.getPlayers()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + deadPlayerWasContestant.getChatColor() + deadPlayerWasContestant.getColorName() + " &f is vermoord door " + killerWasContestant.getChatColor() + killerWasContestant.getColorName()));
                        if (killerWasContestant.getPeacekeeper()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&fDe &bPeacekeeper heeft &7&l(" + killerWasContestant.getPeacekeeperKills() + "&7&l) &fkills gemaakt"));
                        }
                    }
                    return;
                }

                // if deadplayer was contestant
                if (deadPlayerWasContestant != null) {
                    try {
                        deadPlayerWasContestant.setDeath(true);
                        game.updateContestant(deadPlayerWasContestant);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }

                    for (Player player : world.getPlayers()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + deadPlayerWasContestant.getChatColor() + deadPlayerWasContestant.getColorName() + " &fis dood gegaan"));
                    }
                    return;
                }
            }

            deadPlayer.spigot().respawn();
            deadPlayer.teleport(deathLocation);
        }
    }
}
