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
                    return;
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
                    return;
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
}
