package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ASyncPlayerChat implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onASyncPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        // Clear everyone from the receiving list
        e.getRecipients().clear();
        // Get your current world
        World world = Bukkit.getServer().getWorld(player.getWorld().getUID());

        // Add everyone in your world
        for (Player singlePlayer : world.getPlayers()) {
            e.getRecipients().add(singlePlayer);
        }

        if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) return;

        Game game = plugin.getSQL().sqlSelect.worldGame(player.getWorld().getUID());
        // Check if game is null
        if (game == null) return;

        // Player is in a world thats a game
        if (!plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) {
            // Check if player is host
            boolean isHost = false;
            for (UUID singleHost : game.getHosts()) {
                if (singleHost.equals(player.getUniqueId())) {
                    isHost = true;
                    break;
                }
            }

            if (!isHost) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan alleen praten als host of als speler!"));
            }
        }
        // Check if map is not this world
        if (!game.getMap().equals(player.getWorld().getUID())) return;

        Contestant contestant = null;
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(player.getUniqueId())) {
                contestant = singleContestant;
                break;
            }
        }

        // Check if contestant is real
        if (contestant != null) {
            if (contestant.getDeath()) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan niet praten terwijl je dood bent!"));
            }
        }
    }
}
