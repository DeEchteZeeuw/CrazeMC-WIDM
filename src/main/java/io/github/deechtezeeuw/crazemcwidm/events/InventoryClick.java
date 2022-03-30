package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.*;
import io.github.deechtezeeuw.crazemcwidm.gui.books.DeathNote;
import io.github.deechtezeeuw.crazemcwidm.gui.books.Reborn;
import io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.*;
import io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.weaponsSubs.BowsMenu;
import io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.weaponsSubs.OthersMenu;
import io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.weaponsSubs.SwordsMenu;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        // Click while having Queue panel open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new QueuePanel().title())) + "queue panel")) queuePanelInteraction(e);

        // Click while having Items menu open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ItemsMenu().title())))) itemsMenuInteraction(e);

        // Click while having Weapons menu open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new WeaponsMenu().title())))) weaponsMenuInteraction(e);

        // Click while having a sub menu items open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new GearMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonGearMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new LeatherMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonLeatherMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new RolesMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ToolsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BlocksMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BooksMenu().title())))) itemsObjectMenuInteraction(e);

        // Click while having a sub menu of weapons open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new SwordsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BowsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new OthersMenu().title())))) weaponsObjectMenuInteraction(e);

        /* BOOKS SECTION */

        // Click while having deathnote gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new DeathNote().title())))) deathnoteMenuInteraction(e);

        // Click while having reborn gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Reborn().title())))) rebornMenuInteraction(e);
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
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;
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
            if (player.getWorld().getUID().equals(game.getMap())) {
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
            plugin.getGameManager().setGamesThatStarted(game.getUuid());
            game.updateTimer();
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

        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("geen speler(s) gevonden >>")) {
            new PanelMenu().open(player);
            return;
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
        Contestant contestant = null;

        for (ItemStack items : e.getClickedInventory().getStorageContents()) {
            if (items != null) {
                if (items.getType() != null) {
                    for (Contestant singleContestant : game.getContestant()) {
                        if (items.getType().equals(singleContestant.getShulkerMaterial())) contestant = singleContestant;
                    }
                }
            }
        }

        if (contestant == null) return;

        // Player
        if (clickedItem.getType().equals(Material.SKULL_ITEM) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Speler >>")) {
            if (contestant.getPlayer() == null) {
                new QueuePanel().openForColor(contestant, player);
                return;
            }
        }
        if (contestant.getPlayer() != null && clickedItem.getType().equals(Material.SKULL_ITEM) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase(contestant.getPlayername() + " >>")) {
            UUID oldColor = contestant.getPlayer();
            contestant.setPlayer(null);
            contestant.getPlayer();
            try {
                plugin.getSQL().sqlUpdate.updateContestant(contestant, "player");
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                return;
            }

            player.closeInventory();
            new ColorPanel().openColor(contestant, player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de speler verwijderd van de kleur!"));
            // TP player to lobby
            if (Bukkit.getServer().getPlayer(oldColor) != null) {
                Bukkit.getServer().getPlayer(oldColor).teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            }

        }

        // Role
        if (clickedItem.getType().equals(Material.COMPASS) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Role >>")) {
            switch (contestant.getRole()) {
                case 0:
                    contestant.setRole(1);
                    break;
                case 1:
                    contestant.setRole(2);
                    break;
                case 2:
                    contestant.setRole(3);
                    break;
                default:
                    contestant.setRole(0);
            }

            try {
                plugin.getSQL().sqlUpdate.updateContestant(contestant, "role");
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                return;
            }

            player.closeInventory();
            new ColorPanel().openColor(contestant, player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de role aangepast!"));
            return;
        }

        // Kills
        if (clickedItem.getType().equals(Material.IRON_SWORD) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Kill(s) >>")) {
            // Kill toevoegen
            if (e.isLeftClick()) {
                contestant.setKills(contestant.getKills() + 1);

                try {
                    plugin.getSQL().sqlUpdate.updateContestant(contestant, "kills");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                    return;
                }
                player.closeInventory();
                new ColorPanel().openColor(contestant, player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de kills geupdate!"));
                return;
            }
            // Kill verwijderen
            if (contestant.getKills() >= 1) {
                if (e.isRightClick()) {
                    contestant.setKills(contestant.getKills() - 1);

                    try {
                        plugin.getSQL().sqlUpdate.updateContestant(contestant, "kills");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                        return;
                    }
                    player.closeInventory();
                    new ColorPanel().openColor(contestant, player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de kills geupdate!"));
                    return;
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe speler heeft al 0 kills!"));
            }
            return;
        }

        // Set death
        if (clickedItem.getType().equals(Material.POTION) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("dood >>")) {
            if (contestant.getDeath()) {
                contestant.setDeath(false);
            } else {
                contestant.setDeath(true);
            }

            try {
                plugin.getSQL().sqlUpdate.updateContestant(contestant, "death");
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iet fout gegaan!"));
                return;
            }
            player.closeInventory();
            new ColorPanel().openColor(contestant, player);
            if (Bukkit.getServer().getPlayer(contestant.getUuid()) != null) {
                Bukkit.getServer().getPlayer(contestant.getUuid()).teleport(player.getLocation());
                Bukkit.getServer().getPlayer(contestant.getUuid()).setGameMode(GameMode.ADVENTURE);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de death aangepast!"));
            return;
        }

        // Set peacekeeper
        if (clickedItem.getType().equals(Material.DIAMOND_HELMET) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Peacekeeper >>")) {
            if (contestant.getPeacekeeper()) {
                contestant.setPeacekeeper(false);
            } else {
                contestant.setPeacekeeper(true);
            }

            try {
                plugin.getSQL().sqlUpdate.updateContestant(contestant, "peacekeeper");
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iet fout gegaan!"));
                return;
            }
            player.closeInventory();
            new ColorPanel().openColor(contestant, player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de Peacekeeper aangepast!"));
            return;
        }

        // PK Kills
        if (clickedItem.getType().equals(Material.DIAMOND_SWORD) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("PK Kill(s) >>")) {
            // Kill toevoegen
            if (e.isLeftClick()) {
                contestant.setPeacekeeperKills(contestant.getPeacekeeperKills() + 1);

                try {
                    plugin.getSQL().sqlUpdate.updateContestant(contestant, "pkkills");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                    return;
                }
                player.closeInventory();
                new ColorPanel().openColor(contestant, player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de Peacekeeper kills geupdate!"));
                return;
            }
            // Kill verwijderen
            if (contestant.getPeacekeeperKills() >= 1) {
                if (e.isRightClick()) {
                    contestant.setPeacekeeperKills(contestant.getPeacekeeperKills() - 1);

                    try {
                        plugin.getSQL().sqlUpdate.updateContestant(contestant, "pkkills");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                        return;
                    }
                    player.closeInventory();
                    new ColorPanel().openColor(contestant, player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de Peacekeeper kills geupdate!"));
                    return;
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe speler heeft al 0 Peacekeeper kills!"));
            }
            return;
        }

        // Set color spawn
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

    // Queue panel
    protected void queuePanelInteraction(InventoryClickEvent e) {
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
        Contestant contestant = null;
        Player wantAsColor = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll(">> Kleur: ", "").equalsIgnoreCase(singleContestant.getColorName())) contestant = singleContestant;
        }

        if (contestant == null) return;

        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("geen spelers in de queue >>")) {
            new ColorPanel().openColor(contestant, player);
            return;
        }

        if (Bukkit.getServer().getPlayer(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "")) != null) wantAsColor = Bukkit.getServer().getPlayer(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", ""));

        contestant.setPlayer(wantAsColor.getUniqueId());

        try {
            plugin.getSQL().sqlUpdate.updateContestant(contestant, "player");
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        if (plugin.getGameManager().getQueue().contains(wantAsColor.getUniqueId())) {
            plugin.getGameManager().setQueue(wantAsColor.getUniqueId());
        }
        player.closeInventory();
        new ColorPanel().openColor(contestant, player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de speler aangepast!"));

        // TP player to game
        if (contestant.getSpawn() != null) {
            wantAsColor.teleport(contestant.getSpawn());
            wantAsColor.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aJe wordt naar je kleur geteleporteerd!"));
        } else {
            wantAsColor.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe kleur spawn is nog niet neergezet, neem contact op met de host of gebruik /game!"));
        }
        return;
    }

    // Items menu
    protected void itemsMenuInteraction(InventoryClickEvent e) {
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

        // Enchanted gear
        if (clickedItem.getType().equals(Material.DIAMOND_CHESTPLATE) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Enchanted gear >>")) {
            new GearMenu().open(player);
        }
        // Non enchanted gear
        if (clickedItem.getType().equals(Material.DIAMOND_CHESTPLATE) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Non enchanted gear >>")) {
            new NonGearMenu().open(player);
        }
        // Enchanted leather gear
        if (clickedItem.getType().equals(Material.LEATHER_CHESTPLATE) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Enchanted leather gear >>")) {
            new LeatherMenu().open(player);
        }
        // Non enchanted leather gear
        if (clickedItem.getType().equals(Material.LEATHER_CHESTPLATE) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Non enchanted leather gear >>")) {
            new NonLeatherMenu().open(player);
        }
        // Weapons gear
        if (clickedItem.getType().equals(Material.DIAMOND_SWORD) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Weapons >>")) {
            new WeaponsMenu().open(player);
        }
        // Roles
        if (clickedItem.getType().equals(Material.PAPER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Roles >>")) {
            new RolesMenu().open(player);
        }
        // Tools
        if (clickedItem.getType().equals(Material.DIAMOND_PICKAXE) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Tools >>")) {
            new ToolsMenu().open(player);
        }
        // Blocks menu
        if (clickedItem.getType().equals(Material.GRASS) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Blocks >>")) {
            new BlocksMenu().open(player);
        }
        // Books menu
        if (clickedItem.getType().equals(Material.BOOK) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Books >>")) {
            new BooksMenu().open(player);
        }
        // Back to panel
        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Terug naar panel >>")) {
            new PanelMenu().open(player);
        }
    }

    // Items object menu
    protected void itemsObjectMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());

        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;
        // Back to panel
        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Terug naar items >>")) {
            new ItemsMenu().open(player);
            return;
        }
        // Next page
        if (clickedItem.getType().equals(Material.ARROW) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Volgende pagina >>")) {
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new LeatherMenu().title())))) {
                new LeatherMenu().openPage(player, 1);
                return;
            }
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonLeatherMenu().title())))) {
                new NonLeatherMenu().openPage(player, 1);
                return;
            }
            return;
        }
        // Previous page
        if (clickedItem.getType().equals(Material.ARROW) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Vorige pagina >>")) {
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new LeatherMenu().title())))) {
                new LeatherMenu().openPage(player, 0);
                return;
            }
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonLeatherMenu().title())))) {
                new NonLeatherMenu().openPage(player, 0);
                return;
            }
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe inventory zit vol!"));
            return;
        }

        ItemMeta newItemMeta = clickedItem.getItemMeta();
        newItemMeta.setLore(new ArrayList<>());
        clickedItem.setItemMeta(newItemMeta);

        player.getInventory().addItem(clickedItem);
    }

    // Weapons menu
    protected void weaponsMenuInteraction(InventoryClickEvent e) {
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

        // Swords menu
        if (clickedItem.getType().equals(Material.DIAMOND_SWORD) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Swords >>")) {
            new SwordsMenu().open(player);
            return;
        }

        // Bows menu
        if (clickedItem.getType().equals(Material.BOW) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Bows >>")) {
            new BowsMenu().open(player);
            return;
        }

        // Others menu
        if (clickedItem.getType().equals(Material.STICK) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Others >>")) {
            new OthersMenu().open(player);
            return;
        }

        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;
        // Back to panel
        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Terug naar items >>")) {
            new ItemsMenu().open(player);
            return;
        }
    }

    // Weapons object menu
    protected void weaponsObjectMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;
        // Back to panel
        if (clickedItem.getType().equals(Material.BARRIER) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Terug naar weapons >>")) {
            new WeaponsMenu().open(player);
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe inventory zit vol!"));
            return;
        }

        ItemMeta newItemMeta = clickedItem.getItemMeta();
        newItemMeta.setLore(new ArrayList<>());
        clickedItem.setItemMeta(newItemMeta);
        if (clickedItem.getType().equals(Material.ARROW) || clickedItem.getType().equals(Material.TIPPED_ARROW) || clickedItem.getType().equals(Material.SPECTRAL_ARROW)) {
            clickedItem.setAmount(16);
        }

        player.getInventory().addItem(clickedItem);
    }

    /* BOOKS SECTIONS */

    // Deathnote
    protected void deathnoteMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;

        String playername = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        Player onlinePlayer = null;
        OfflinePlayer offlinePlayer = null;

        // Get player if online
        if (Bukkit.getServer().getPlayer(playername) != null) {
            onlinePlayer = Bukkit.getServer().getPlayer(playername);
        }
        // Get offline player if not online
        if (Bukkit.getServer().getOfflinePlayer(playername) != null) {
            offlinePlayer = Bukkit.getServer().getOfflinePlayer(playername);
        }

        if (onlinePlayer == null && offlinePlayer == null) return;
        UUID playerUUID = (onlinePlayer != null) ? onlinePlayer.getUniqueId() : null;
        if (playerUUID == null) playerUUID = (offlinePlayer != null) ? offlinePlayer.getUniqueId() : null;

        if (playerUUID == null) return;
        Game game = null;

        game = plugin.getSQL().sqlSelect.playerGame(playerUUID);

        boolean killerInGame = false;
        boolean toKilledInGame = false;
        Contestant killerContestant = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() != null) {
                if (singleContestant.getPlayer().equals(player.getUniqueId())) {
                    killerInGame = true;
                    killerContestant = singleContestant;
                }
                if (singleContestant.getPlayer().equals(playerUUID)) toKilledInGame = true;
            }
        }

        // Check if both in same game
        if (!killerInGame || !toKilledInGame) return;

        if (game.getGameStatus() != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cKan dit item alleen gebruiken terwijl de game bezig is!"));
            return;
        }

        if (!plugin.getSQL().sqlSelect.playerIsContestant(playerUUID)) return;
        Contestant deathcontestant = plugin.getSQL().sqlSelect.getPlayerContestant(playerUUID);
        try {
            deathcontestant.setDeath(true);
            plugin.getSQL().sqlUpdate.updateContestant(deathcontestant, "death");
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        // kill player
        if (Bukkit.getServer().getPlayer(deathcontestant.getPlayer()) != null) {
            Player deathPlayer = Bukkit.getServer().getPlayer(deathcontestant.getPlayer());
            deathPlayer.setHealth(0.0D);
        }

        // Check if world exists
        if (game.getMap() != null) {
            if (Bukkit.getServer().getWorld(game.getMap()) != null) {
                for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
                    worldPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + killerContestant.getChatColor() + killerContestant.getPlayername() + " &fheeft " + deathcontestant.getChatColor() + deathcontestant.getPlayername() + " &fgedeathnote!"));
                }
            }
        }
    }

    // Reborn
    protected void rebornMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;

        String playername = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        Player onlinePlayer = null;
        OfflinePlayer offlinePlayer = null;

        // Get player if online
        if (Bukkit.getServer().getPlayer(playername) != null) {
            onlinePlayer = Bukkit.getServer().getPlayer(playername);
        }
        // Get offline player if not online
        if (Bukkit.getServer().getOfflinePlayer(playername) != null) {
            offlinePlayer = Bukkit.getServer().getOfflinePlayer(playername);
        }

        if (onlinePlayer == null && offlinePlayer == null) return;
        UUID rebornUUID = (onlinePlayer != null) ? onlinePlayer.getUniqueId() : null;
        if (rebornUUID == null) rebornUUID = (offlinePlayer != null) ? offlinePlayer.getUniqueId() : null;

        if (rebornUUID == null) return;

        // Get game from user
        if (!plugin.getSQL().sqlSelect.playerIsContestant(player.getUniqueId())) return;
        Game game = plugin.getSQL().sqlSelect.playerGame(player.getUniqueId());

        // Check if game is null
        if (game == null) return;

        // Check if both players are in same game
        boolean rebornerIsInGame = false;
        boolean rebornIsInGame = false;
        Contestant rebornerContestant = null;
        Contestant rebornContestant = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() != null) {
                if (singleContestant.getPlayer().equals(player.getUniqueId())) {
                    rebornerIsInGame = true;
                    rebornerContestant = singleContestant;
                }
                if (singleContestant.getPlayer().equals(rebornUUID)) {
                    rebornIsInGame = true;
                    rebornContestant = singleContestant;
                }
            }
        }

        // Check
        if (!rebornerIsInGame || !rebornIsInGame || rebornContestant == null) return;

        // Update
        try {
            rebornContestant.setDeath(false);
            plugin.getSQL().sqlUpdate.updateContestant(rebornContestant, "death");
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
            return;
        }

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        // Check if world exists
        if (game.getMap() != null) {
            if (Bukkit.getServer().getWorld(game.getMap()) != null) {
                for (Player worldPlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
                    worldPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + rebornerContestant.getChatColor() + rebornerContestant.getPlayername() + " &fheeft " + rebornContestant.getChatColor() + rebornContestant.getPlayername() + " &ftot leven gebracht!"));
                }
            }
        }

        if (Bukkit.getServer().getPlayer(rebornUUID) != null) {
            Player rebornPlayer = Bukkit.getServer().getPlayer(rebornUUID);
            if (rebornPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
                if (game.getMap().equals(player.getWorld().getUID())) {
                    rebornPlayer.teleport(player.getLocation());
                    if (!rebornPlayer.getGameMode().equals(GameMode.ADVENTURE)) {
                        rebornPlayer.setGameMode(GameMode.ADVENTURE);
                    }
                }
            }
        }
    }
}
