package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItem implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        for (HumanEntity singlePlayer : e.getViewers()) {
            Player player = (Player) singlePlayer;

            if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) return;

            // Player is in a world thats a game
            if (!plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) return;

            Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());
            // Check if game is null
            if (game == null) return;
            // Check if map is not this world
            if (!game.getMap().equals(player.getWorld().getUID())) return;

            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cCraften tijdens WIDM is niet toegestaan!"));
        }
    }
}
