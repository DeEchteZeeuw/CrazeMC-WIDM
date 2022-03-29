package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class PlayerInteract implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();
    private ArrayList<Material> shulkers = new ArrayList<>();

    public PlayerInteract() {
        shulkers.add(Material.BLACK_SHULKER_BOX);
        shulkers.add(Material.BLUE_SHULKER_BOX);
        shulkers.add(Material.CYAN_SHULKER_BOX);
        shulkers.add(Material.GRAY_SHULKER_BOX);
        shulkers.add(Material.GREEN_SHULKER_BOX);
        shulkers.add(Material.LIGHT_BLUE_SHULKER_BOX);
        shulkers.add(Material.SILVER_SHULKER_BOX);
        shulkers.add(Material.LIME_SHULKER_BOX);
        shulkers.add(Material.MAGENTA_SHULKER_BOX);
        shulkers.add(Material.ORANGE_SHULKER_BOX);
        shulkers.add(Material.PINK_SHULKER_BOX);
        shulkers.add(Material.PURPLE_SHULKER_BOX);
        shulkers.add(Material.RED_SHULKER_BOX);
        shulkers.add(Material.WHITE_SHULKER_BOX);
        shulkers.add(Material.YELLOW_SHULKER_BOX);
    }

    @EventHandler
    public void onPlayerInterect(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getClickedBlock() == null) return;
        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock.getType() == null) return;

        // Check if player is contestsant
        if (!plugin.getSQL().sqlSelect.mapExists(player.getWorld().getUID())) return;

        // Player is in a world thats a game
        if (!plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) return;

        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());
        // Check if game is null
        if (game == null) return;
        // Check if map is not this world
        if (!game.getMap().equals(player.getWorld().getUID())) return;

        // Check if player is interacting with chest while not playing
        if (game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.CHEST) ||
            game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.TRAPPED_CHEST) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.HOPPER) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.HOPPER_MINECART) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.ANVIL) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.WORKBENCH) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.ENCHANTMENT_TABLE) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.ENDER_CHEST) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.FURNACE) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BURNING_FURNACE) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BED) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.DISPENSER) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.DROPPER) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.OBSERVER) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.MINECART) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.COMMAND_MINECART) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.EXPLOSIVE_MINECART) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.STORAGE_MINECART) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT_ACACIA) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT_BIRCH) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT_DARK_OAK) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT_JUNGLE) ||
                game.getGameStatus() != 1 && clickedBlock.getType().equals(Material.BOAT_SPRUCE)) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan geen chest openen zolang de game nog niet is begonnen!"));
        }
        // Check if player is interacting with shulker
        if (shulkers.contains(clickedBlock.getType())) checkIfUserCanOpenShulker(e, game, player, clickedBlock);
    }

    protected void checkIfUserCanOpenShulker(PlayerInteractEvent e, Game game, Player player, Block clickedBlock) {
        // Game is not playing so deny acces
        if (game.getGameStatus() != 1) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe game is niet actief! Je kan nog geen shulkers openen!"));
            return;
        }

        // Get player contestant role
        Contestant contestant = null;
        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(player.getUniqueId())) {
                contestant = singleContestant;
                break;
            }
        }

        // Check if contestant is null
        if (contestant == null) return;

        // Check if user is allowed to open shulker
        if (!contestant.getShulkerMaterial().equals(clickedBlock.getType())) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe mag alleen je eigen kleur shulker openen!"));
        }
    }
}
