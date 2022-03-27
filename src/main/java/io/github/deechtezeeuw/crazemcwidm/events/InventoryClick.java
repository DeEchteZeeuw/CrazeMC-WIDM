package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.*;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class InventoryClick implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView() == null) return;
        if (e.getView().getTitle() == null) return;

        // Click while having Host Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new HostMenu().title()))) hostMenuInteraction(e);

        // Click while having Map Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new MapMenu().title()))) mapMenuInteraction(e);

        // Click while having Game Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new GameMenu().title()))) gameMenuInteraction(e);

        // Click while having Host Panel open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new PanelMenu().title()))) hostPanelInteraction(e);

        // Click while having Hosts Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new HostsMenu().title()))) hostsMenuInteraction(e);

        // Click while having Color panel open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ColorPanel().title())) + "color panel")) colorPanelInteraction(e);
    }

    // Host menu
    protected void hostMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        // Check if there is an player
        if (e.getWhoClicked() == null) return;
        Player player = (Player) e.getWhoClicked();

        // Check if item was clicked
        if (e.getCurrentItem() == null) return;
        ItemStack item = e.getCurrentItem();

        // Check if item has meta
        if (item.getItemMeta() == null) return;
        ItemMeta itemMeta = item.getItemMeta();

        // Check if there is an title
        if (itemMeta.getDisplayName().isEmpty()) return;

        // Stripcolor
        String title = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        // Check if displayname was Tempvuller
        if (title.equalsIgnoreCase(ChatColor.stripColor(">> Tempvuller <<"))) {
            // Check if player has permissions
            if (!player.hasPermission("crazemc.tempvuller")) {
                plugin.getCommandManager().noPermission(player, null);
                return;
            }
            new MapMenu().open(player);
            return;
        }

        // Check if displayname was jrgamehost
        if (title.equalsIgnoreCase(ChatColor.stripColor(">> jr gamehost <<"))) {
            // Check if player has permissions
            if (!player.hasPermission("crazemc.jrgamehost")) {
                plugin.getCommandManager().noPermission(player, null);
                return;
            }
            new MapMenu().open(player);
            return;
        }

        // Check if displayname was gamehost
        if (title.equalsIgnoreCase(ChatColor.stripColor(">> gamehost <<"))) {
            // Check if player has permissions
            if (!player.hasPermission("crazemc.gamehost")) {
                plugin.getCommandManager().noPermission(player, null);
                return;
            }
            new MapMenu().open(player);
            return;
        }

        // Check if displayname was srgamehost
        if (title.equalsIgnoreCase(ChatColor.stripColor(">> sr gamehost <<"))) {
            // Check if player has permissions
            if (!player.hasPermission("crazemc.srgamehost")) {
                plugin.getCommandManager().noPermission(player, null);
                return;
            }
            new MapMenu().open(player);
        }
    }

    // Map menu
    protected void mapMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;

        // Strip title
        String strippedTitle = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(">> ", "");

        // Check if its not the map available or map not available wool.
        if (strippedTitle.equalsIgnoreCase("map beschikbaar") || strippedTitle.equalsIgnoreCase("map niet beschikbaar")) return;

        // Check if its red wool (map not available)
        if (clickedItem.getType().equals(Material.WOOL)) {
            if (clickedItem.getData().getData() == 14) return;
        }

        // Check if its the unclaim wool and player is hosting an game
        if (strippedTitle.equalsIgnoreCase("unclaim") && plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

            try {
                plugin.getGameManager().deleteGame(game);
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is een fout opgetreden!"));
                return;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de game geunclaimed!"));
            player.closeInventory();
            return;
        }

        // Check if user is already an host
        if (plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kan geen game hosten, aangezien je er al 1 host. Unclaim deze eerst."));
            return;
        }

        // Close inventory
        player.closeInventory();

        // Create unique game code
        UUID uuid = UUID.randomUUID();
        String newWorld = strippedTitle + "-" + uuid;

        // Create world through Multiverse
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "mv clone " + strippedTitle + " " + newWorld;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        // Check if world exists
        if (Bukkit.getServer().getWorld(newWorld) == null) {
            console.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().consolePrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan bij het aanmaken van wereld: "+newWorld));
            return;
        }
        World world = Bukkit.getServer().getWorld(newWorld);

        // Set game variables
        Game game = new Game(uuid);
        game.setMap(world.getUID());
        game.setHost(player.getUniqueId());
        game.setTheme(strippedTitle.toLowerCase().replaceAll("\\s*", ""));
        game.setGameStatus(0);

        try {
            plugin.getGameManager().createGame(game);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        // Tp host to the world
        player.teleport(world.getSpawnLocation());
    }

    // Game menu
    protected void gameMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Get game of the player
        Game game = null;
        boolean host = false;
        if (plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());
            host = true;
        }
        if (plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) {
            game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());
        }
        // If there is no game found then close it
        if (game == null) {
            player.closeInventory();
            return;
        }

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;

        // Strip title
        String strippedTitle = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(" >>", "");

        if (strippedTitle.equalsIgnoreCase("teleport")) {
            if (!player.getWorld().getUID().equals(game.getMap())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent al in de game!"));
                return;
            }

            if (host) {
                if (Bukkit.getServer().getWorld(game.getMap()) != null) {
                    player.teleport(Bukkit.getServer().getWorld(game.getMap()).getSpawnLocation());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aJe wordt naar de wereld geteleporteerd!"));
                    return;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is een fout opgetreden neem contact op met de host!"));
                    return;
                }
            } else {
                for (Contestant contestant : game.getContestant()) {
                    if (contestant.getPlayer() == null) continue;
                    if (contestant.getPlayer().equals(player.getUniqueId())) {
                        // Check if spawn of the player is set
                        if (contestant.getSpawn() != null) {
                            player.teleport(contestant.getSpawn());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aJe wordt naar de wereld geteleporteerd!"));
                            return;
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJouw kleur bevat nog geen spawn locatie!"));
                            return;
                        }
                    }
                }
            }
        }
    }

    // Host panel
    protected void hostPanelInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;

        // Strip title
        String strippedTitle = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(" >>", "");

        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        // Start game
        if (strippedTitle.equalsIgnoreCase("start game") && clickedItem.getData().getData() == 5) {

            try {
                game.setGameStatus(1);
                plugin.getSQL().sqlUpdate.updateGame(game, "gamestatus");
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        // Pause game
        if (strippedTitle.equalsIgnoreCase("pauzeer game") && clickedItem.getData().getData() == 9) {

            try {
                game.setGameStatus(2);
                plugin.getSQL().sqlUpdate.updateGame(game, "gamestatus");
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        // Resume game
        if (strippedTitle.equalsIgnoreCase("hervat game") && clickedItem.getData().getData() == 5) {

            try {
                game.setGameStatus(1);
                plugin.getSQL().sqlUpdate.updateGame(game, "gamestatus");
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);
            return;
        }

        // Host(s) menu
        if (strippedTitle.equalsIgnoreCase("host(s)") && clickedItem.getType().equals(Material.SKULL_ITEM)) {
            player.closeInventory();
            new HostsMenu().open(player);
            return;
        }

        // Items menu
        if (strippedTitle.equalsIgnoreCase("items menu") && clickedItem.getType().equals(Material.WORKBENCH)) {
            player.closeInventory();
            new ItemsMenu().open(player);
            return;
        }

        // Unclaim game
        if (strippedTitle.equalsIgnoreCase("unclaim") && clickedItem.getData().getData() == 4) {

            try {
                plugin.getGameManager().deleteGame(game);
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is een fout opgetreden!"));
                return;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de game geunclaimed!"));
            player.closeInventory();
            return;
        }

        // Color
        for (Contestant contestant : game.getContestant()) {
            if (clickedItem.getType().equals(contestant.getShulkerMaterial())) {
                new ColorPanel().openColor(contestant, player);
                return;
            }
        }
    }

    // Hosts menu
    protected void hostsMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;
        if (clickedItem.getItemMeta().getLore() == null) return;

        // Check if skull
        if (clickedItem.getType().equals(Material.SKULL_ITEM)) {
            String strippedLore = ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0));
            strippedLore = strippedLore.replaceAll(">> ", "");

            // Delete as host
            if (strippedLore.equalsIgnoreCase("klik hier om hem/haar te verwijderen als host")) {
                Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

                String playername = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(" >>", "");

                UUID uuid =(Bukkit.getServer().getPlayer(playername) != null ? Bukkit.getServer().getPlayer(playername).getUniqueId() : Bukkit.getServer().getOfflinePlayer(playername).getUniqueId() );
                game.setHost(uuid);

                plugin.getSQL().sqlUpdate.updateGame(game, "hosts");
                player.closeInventory();
                if (Bukkit.getServer().getPlayer(uuid) != null) Bukkit.getServer().getPlayer(uuid).teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de host(s) aangepast!"));
                new HostsMenu().open(player);
            }
        }
    }

    // Color panel
    protected void colorPanelInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;
        if (clickedItem.getItemMeta().getLore() == null) return;

        if (!plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverPrefix + "&cJe bent geen host"));
            return;
        }

        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        for (Contestant contestant : game.getContestant()) {
            if (clickedItem.getType().equals(contestant.getShulkerMaterial())) {
                contestant.setSpawn(player.getLocation());

                try {
                    plugin.getSQL().sqlUpdate.updateContestant(contestant, "spawn");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                    return;
                }
                player.closeInventory();
                new ColorPanel().openColor(contestant, player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de spawn geupdate!"));
                return;
            }
        }
    }
}
