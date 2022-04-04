package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.PanelMenu;
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

        Game game = (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) ? plugin.getGameDataManager().getWorldGame(player.getWorld().getUID()) : null;

        if (game == null) return;
        if (player.hasPermission("crazemc.bypass.chat") || game.isHost(player.getUniqueId())) return;

        Contestant contestant = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (contestant.getPlayer() == null) continue;
            if (contestant.getPlayer().equals(player.getUniqueId())) {
                contestant = singleContestant;
                break;
            }
        }

        if (contestant == null) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe mag alleen praten als host of als speler!"));
            return;
        }

        if (contestant.getDeath()) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe mag niet praten als je dood bent!"));
            return;
        }
    }
}
