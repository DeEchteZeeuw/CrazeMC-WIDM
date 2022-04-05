package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceEvent implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Game game = (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) ? plugin.getGameDataManager().getWorldGame(player.getWorld().getUID()) : null;

        if (game == null) return;

        if (!game.isContestant(player.getUniqueId()) && game.getGameStatus() != 1) return;

        Contestant contestant = game.getContestant(player.getUniqueId());

        Block placedBlock = e.getBlockPlaced();
        Block placedAgainst = e.getBlockAgainst();

        if (placedAgainst.getType().equals(Material.ENDER_STONE)) {
            if (placedAgainst.getFace(placedBlock).equals(BlockFace.UP) && (placedBlock.getType().equals(Material.GOLD_BLOCK) || placedBlock.getType().equals(Material.DIAMOND_BLOCK))) {
                if (contestant.getRole() == 3 && placedBlock.getType().equals(Material.GOLD_BLOCK)) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen met diamond block finishen als mol!"));
                    return;
                }

                // Player win
                if (placedBlock.getType().equals(Material.GOLD_BLOCK)) {
                    game.setGameStatus(3);
                    for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
                        worldPlayer.sendTitle(
                                ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                                ChatColor.translateAlternateColorCodes('&', "&bHet spel is gewonnen door de &b&lSpelers&b!"),
                                1, 60, 1);
                    }
                }
                // Mol win
                if (placedBlock.getType().equals(Material.DIAMOND_BLOCK)) {
                    game.setGameStatus(3);
                    for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
                        worldPlayer.sendTitle(
                                ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                                ChatColor.translateAlternateColorCodes('&', "&bHet spel is gewonnen door de &b&lMollen&b!"),
                                1, 60, 1);
                    }
                }

                List<String> message = new ArrayList<>();

                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &c&lAfgelopen &7&m&l]----------------"));

                String Players = "";
                String Egoists = "";
                String Mollen = "";
                String Peacekeepers = "";

                for(Contestant singleContestant : game.getContestant()) {
                    if (singleContestant.getPlayer() == null) continue;
                    // If player is player
                    if (singleContestant.getRole() == 1) {
                        if (Players.length() == 0) {
                            Players += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        } else {
                            Players += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        }
                    }
                    // If player is egoist
                    if (singleContestant.getRole() == 2) {
                        if (Egoists.length() == 0) {
                            Egoists += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        } else {
                            Egoists += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        }
                    }
                    // If player is mol
                    if (singleContestant.getRole() == 3) {
                        if (Mollen.length() == 0) {
                            Mollen += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        } else {
                            Mollen += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        }
                    }
                    // If player is peacekeeper
                    if (singleContestant.getPeacekeeper()) {
                        if (Peacekeepers.length() == 0) {
                            Peacekeepers += singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        } else {
                            Peacekeepers += "&f, " + singleContestant.getChatColor() + "✤ &f" + singleContestant.getPlayername();
                        }
                    }
                }

                // Spelers
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&5&lSpelers: &f" + Players));
                // Egoïst
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&7&lEgoïsten: &f" + Egoists));
                // Mollen
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&b&lMollen: &f" + Mollen));
                // Peacekeeper
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&a&lPeacekeeper: &f" + Peacekeepers));
                // Finisher
                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&dFinisher: "+contestant.getChatColor()+"✤ &c"+contestant.getPlayername()));

                message.add(ChatColor.translateAlternateColorCodes('&',
                        "&7&m&l----------------[ " + plugin.getConfigManager().getMain().serverPrefix + " &c&lAfgelopen &7&m&l]----------------"));
            } else {
                e.setCancelled(true);
            }
        }
    }
}
