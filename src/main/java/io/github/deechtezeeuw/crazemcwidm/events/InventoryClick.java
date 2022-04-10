package io.github.deechtezeeuw.crazemcwidm.events;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.*;
import io.github.deechtezeeuw.crazemcwidm.gui.books.*;
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

import java.util.*;

public class InventoryClick implements Listener {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView() == null) return;
        if (e.getView().getTitle() == null) return;

        // Click while having Host Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new HostMenu().title()))) {
            hostMenuInteraction(e);
            return;
        }

        // Click while having Map Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new MapMenu().title()))) {
            mapMenuInteraction(e);
            return;
        }

        // Click while having Game Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new GameMenu().title()))) {
            gameMenuInteraction(e);
            return;
        }

        // Click while having Host Panel open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new PanelMenu().title()))) {
            hostPanelInteraction(e);
            return;
        }

        // Click while having Hosts Menu open
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', new HostsMenu().title()))) {
            hostsMenuInteraction(e);
            return;
        }

        // Click while having Color panel open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ColorPanel().title())) + "color panel")) {
            colorPanelInteraction(e);
            return;
        }

        // Click while having Queue panel open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new QueuePanel().title())) + "queue panel")) {
            queuePanelInteraction(e);
            return;
        }

        // Click while having Items menu open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ItemsMenu().title())))) {
            itemsMenuInteraction(e);
            return;
        }

        // Click while having Weapons menu open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new WeaponsMenu().title())))) {
            weaponsMenuInteraction(e);
            return;
        }

        // Click while having a sub menu items open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new GearMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonGearMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new LeatherMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new NonLeatherMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new RolesMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ToolsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BlocksMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BooksMenu().title())))) {
            itemsObjectMenuInteraction(e);
            return;
        }

        // Click while having a sub menu of weapons open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new SwordsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new BowsMenu().title()))) ||
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new OthersMenu().title())))) {
            weaponsObjectMenuInteraction(e);
            return;
        }

        /* BOOKS SECTION */

        // Click while having deathnote gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new DeathNote().title())))) {
            deathnoteMenuInteraction(e);
            return;
        }

        // Click while having reborn gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Reborn().title())))) {
            rebornMenuInteraction(e);
            return;
        }

        // Click while having teleport gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Teleport().title())))) {
            teleportMenuInteraction(e);
            return;
        }

        // Click while having pk check gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new PKCheck().title())))) {
            pkcheckMenuInteraction(e);
            return;
        }

        // Click while having switch gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Switch().title())))) {
            switchMenuInteraction(e);
            return;
        }

        // Click while having invsee gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Invsee().title())))) {
            invseeMenuInteraction(e);
            return;
        }

        // Click while having invseePlayer gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new InvseePlayer().title())))) {
            invseePlayerMenuInteraction(e);
            return;
        }

        // Click while having itemcheck gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ItemCheck().title())))) {
            itemcheckMenuInteraction(e);
            return;
        }

        // Click while having itemclear gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new ItemClear().title())))) {
            itemclearMenuInteraction(e);
            return;
        }

        // Click while having book lock gui open
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new Booklock().title())))) {
            booklockMenuInteraction(e);
            return;
        }

        // Check if the world is in a game
        Player player = (Player) e.getWhoClicked();
        if (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) {
            Game game = (plugin.getGameDataManager().worldIsPartOfGame(player.getWorld().getUID())) ? plugin.getGameDataManager().getWorldGame(player.getWorld().getUID()) : null;
            if (game == null) return;
            // Check if player is not a host
            if (game.isHost(player.getUniqueId())) return;
            if (e.getClickedInventory() == null || e.getClickedInventory().getType() == null) return;

            // If nothing of above and its in top inventory
            if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER) && !e.getClickedInventory().getType().equals(InventoryType.CREATIVE)) {
                // Check if clicked on a item
                if (e.getCurrentItem() != null) {
                    ItemStack item = e.getCurrentItem();
                    // Check if that item has lores
                    if (item.getItemMeta() != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() > 0) {
                        boolean containsLock = false;
                        for (String lore : item.getItemMeta().getLore()) {
                            String strippedLore = ChatColor.stripColor(lore).replaceAll(">> ", "");
                            if (strippedLore.equalsIgnoreCase("locked")) containsLock = true;
                        }

                        if (containsLock) {
                            e.setCancelled(true);
                            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDit item is gelocked!"));
                            return;
                        }
                    }
                }
            } else {
                if (e.getView().getTopInventory() != null) {
                    // Check if clicked on a item
                    if (e.getCurrentItem() != null) {
                        ItemStack item = e.getCurrentItem();
                        // Check if that item has lores
                        if (item.getItemMeta() != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() > 0) {
                            boolean containsLock = false;
                            for (String lore : item.getItemMeta().getLore()) {
                                String strippedLore = ChatColor.stripColor(lore).replaceAll(">> ", "");
                                if (strippedLore.equalsIgnoreCase("locked")) containsLock = true;
                            }

                            if (containsLock) {
                                e.setCancelled(true);
                                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDit item is gelocked!"));
                                return;
                            }
                        }
                    }
                }
            }
        }
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
        if (strippedTitle.equalsIgnoreCase("unclaim") && plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) {
            try {
                plugin.getGameDataManager().unclaimHostingGame(player.getUniqueId());
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is een fout opgetreden!"));
                return;
            }
            player.closeInventory();
            return;
        }

        // Check if user is already an host
        if (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) {
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

        ArrayList<Integer> colorCodes = new ArrayList<>();
        for (int x = 0;x < 15;x++) {
            colorCodes.add(x);
        }

        ArrayList<Contestant> contestants = new ArrayList<>();

        // Add contestants to the database
        for (int i = 0; i < plugin.getConfigManager().getMain().getConfig().getInt("mappen."+game.getTheme()+".max-contestants"); i++) {
            Contestant contestant = new Contestant();
            contestant.setUuid(UUID.randomUUID());
            contestant.setGame(game.getUuid());
            contestant.setRole(0);
            contestant.setKills(0);
            contestant.setDeath(false);
            contestant.setPeacekeeper(false);
            contestant.setPeacekeeperKills(0);
            contestant.setSpawn(null);
            contestant.setBooklock(false);

            // Get random color
            if (colorCodes.size() > 0) {
                Collections.shuffle(colorCodes);
                contestant.setColor(colorCodes.get(0));
                colorCodes.remove(0);
            }

            contestants.add(contestant);
        }

        game.setContestantsList(contestants);

        try {
            plugin.getGameDataManager().insertGame(game);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if (plugin.getGameManager().getQueue().contains(player.getUniqueId())) {
            plugin.getGameManager().getQueue().remove(player.getUniqueId());
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol je game aangemaakt! Je wordt erheen geteleporteerd!"));
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
        if (!plugin.getGameDataManager().alreadyHosting(player.getUniqueId()) && !plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe moet in een game zitten om /game te openen!"));
            return;
        }

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : plugin.getGameDataManager().getContestingGame(player.getUniqueId());

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen juist spel gevonden, het menu is gesloten!"));
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

            if (game.isHost(player.getUniqueId())) {
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

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;
        // Check if there is an game
        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe game die je wilde aanpassen bestaat niet, je menu sluit!"));
            return;
        }

        // Check if you are in the right world
        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld om dit te doen, je menu sluit!"));
            return;
        }

        // Start game
        if (strippedTitle.equalsIgnoreCase("start game") && clickedItem.getData().getData() == 5) {

            try {
                game.setGameStatus(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);
            game.updateTimer();

            for (Player singlePlayer : Bukkit.getWorld(game.getMap()).getPlayers()) {
                singlePlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                        ChatColor.translateAlternateColorCodes('&', "&aHet spel is gestart!"), 1, 60 ,1);
            }
            return;
        }

        // Pause game
        if (strippedTitle.equalsIgnoreCase("pauzeer game") && clickedItem.getData().getData() == 9) {

            try {
                game.setGameStatus(2);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);

            for (Player singlePlayer : Bukkit.getWorld(game.getMap()).getPlayers()) {
                singlePlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                        ChatColor.translateAlternateColorCodes('&', "&5Het spel is gepauzeerd!"), 1, 60 ,1);
            }
            return;
        }

        // Resume game
        if (strippedTitle.equalsIgnoreCase("hervat game") && clickedItem.getData().getData() == 5) {

            try {
                game.setGameStatus(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            player.closeInventory();
            new PanelMenu().open(player);

            game.updateTimer();

            for (Player singlePlayer : Bukkit.getWorld(game.getMap()).getPlayers()) {
                singlePlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMain().serverPrefix),
                        ChatColor.translateAlternateColorCodes('&', "&aHet spel is hervat!"), 1, 60 ,1);
            }
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

        // Reset votes
        if (strippedTitle.equalsIgnoreCase("reset votes") && clickedItem.getType().equals(Material.NOTE_BLOCK)) {
            if (!game.isHost(player.getUniqueId())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cAlleen een host mag votes resetten!"));
                return;
            }

            try {
                plugin.getVoteManager().deleteGameVotes(game.getUuid());
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is iets fout gegaan!"));
                return;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aSuccesvol de votes gereset!"));

            for (Contestant contestant : game.getContestant()) {
                if (contestant.getPlayer() == null) continue;
                if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) continue;
                if (!Bukkit.getServer().getPlayer(contestant.getPlayer()).getWorld().getUID().equals(game.getMap())) continue;
                Bukkit.getServer().getPlayer(contestant.getPlayer()).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aDe votes zijn weggepoetst! Je kan weer voten!"));
            }
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

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;
        // Check if there is an game
        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe game die je wilde aanpassen bestaat niet, je menu sluit!"));
            return;
        }

        // Check if you are in the right world
        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld om dit te doen, je menu sluit!"));
            return;
        }

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

                String playername = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(" >>", "");

                UUID uuid =(Bukkit.getServer().getPlayer(playername) != null ? Bukkit.getServer().getPlayer(playername).getUniqueId() : Bukkit.getServer().getOfflinePlayer(playername).getUniqueId() );
                game.setHost(uuid);

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

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;
        // Check if there is an game
        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe game die je wilde aanpassen bestaat niet, je menu sluit!"));
            return;
        }

        // Check if you are in the right world
        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld om dit te doen, je menu sluit!"));
            return;
        }

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;
        if (clickedItem.getItemMeta().getLore() == null) return;

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

        if (contestant == null) {
            player.closeInventory();
            new PanelMenu().open(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe speler is niet gevonden, je wordt terug naar het paneel gestuurd!"));
            return;
        }

        // Player
        if (clickedItem.getType().equals(Material.SKULL_ITEM) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Speler >>")) {
            if (contestant.getPlayer() == null) {
                new QueuePanel().openForColor(contestant, player);
                return;
            }
        }
        if (contestant.getPlayer() != null && clickedItem.getType().equals(Material.SKULL_ITEM) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase(contestant.getPlayername() + " >>")) {
            UUID oldColor = contestant.getPlayer();
            try {
                contestant.setPlayer(null);
                game.updateContestant(contestant);
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
            return;
        }

        // Role
        if (clickedItem.getType().equals(Material.COMPASS) && ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Role >>")) {
            try {
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
                game.updateContestant(contestant);
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
                try {
                    contestant.setKills(contestant.getKills() + 1);
                    game.updateContestant(contestant);
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
                    try {
                        contestant.setKills(contestant.getKills() - 1);
                        game.updateContestant(contestant);
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
            try {
                if (contestant.getDeath()) {
                    contestant.setDeath(false);
                } else {
                    contestant.setDeath(true);
                }
                game.updateContestant(contestant);
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
            try {
                if (contestant.getPeacekeeper()) {
                    contestant.setPeacekeeper(false);
                } else {
                    contestant.setPeacekeeper(true);
                }
                game.updateContestant(contestant);
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
                try {
                    contestant.setPeacekeeperKills(contestant.getPeacekeeperKills() + 1);
                    game.updateContestant(contestant);
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
                    try {
                        contestant.setPeacekeeperKills(contestant.getPeacekeeperKills() - 1);
                        game.updateContestant(contestant);
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
            try {
                contestant.setSpawn(player.getLocation());
                game.updateContestant(contestant);
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

        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;
        // Check if there is an game
        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe game die je wilde aanpassen bestaat niet, je menu sluit!"));
            return;
        }

        // Check if you are in the right world
        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld om dit te doen, je menu sluit!"));
            return;
        }

        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem.getItemMeta() == null) return;
        if (clickedItem.getItemMeta().getDisplayName() == null) return;
        if (clickedItem.getItemMeta().getLore() == null) return;

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

        try {
            contestant.setPlayer(wantAsColor.getUniqueId());
            game.updateContestant(contestant);
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
        if (clickedItem.getType().equals(Material.PAPER)) {
            String strippedTitle = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");
            if (strippedTitle.equalsIgnoreCase("speler") || strippedTitle.equalsIgnoreCase("egoïst") || strippedTitle.equalsIgnoreCase("mol") || strippedTitle.equalsIgnoreCase("peacekeeper")) {
               List<String> lore = newItemMeta.getLore();
               lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &c&lLocked"));
               newItemMeta.setLore(lore);

                ItemStack newItem = clickedItem;
                newItem.setItemMeta(newItemMeta);

                player.getInventory().addItem(newItem);
                player.closeInventory();
                new RolesMenu().open(player);
                return;
            }
        }
        ItemStack newItem = clickedItem;
        newItem.setItemMeta(newItemMeta);

        player.getInventory().addItem(newItem);
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

        Game game = (plugin.getGameDataManager().alreadyContestant(playerUUID)) ? plugin.getGameDataManager().getContestingGame(playerUUID) : null;

        if (game == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game gevonden, je menu wordt gesloten!"));
            player.closeInventory();
            return;
        }

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

        if (!plugin.getGameDataManager().alreadyContestant(playerUUID)) return;
        Contestant deathcontestant = plugin.getGameDataManager().getContestingContest(game.getUuid(), playerUUID);
        try {
            deathcontestant.setDeath(true);
            game.updateContestant(deathcontestant);
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
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

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
            game.updateContestant(rebornContestant);
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

    // Teleport
    protected void teleportMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;
        String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        // Get UUID
        UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
        if (clickHeadUUID == null) return;

        if (plugin.getGameManager().getTeleportChoice().containsKey(player.getUniqueId())) {
            // Get player you want to teleport
            UUID TeleportPlayer = plugin.getGameManager().getTeleportChoice().get(player.getUniqueId());
            UUID TeleportTo = clickHeadUUID;

            // Check if they are both in same game
            boolean firstCheck = false;
            Contestant TeleportPlayerContestant = null;
            boolean secondCheck = false;
            Contestant TeleportToContestant = null;

            for (Contestant singleContestant : game.getContestant()) {
                if (singleContestant.getPlayer() == null) continue;
                if (singleContestant.getPlayer().equals(TeleportPlayer)) {
                    TeleportPlayerContestant = singleContestant;
                    firstCheck = true;
                }
                if (singleContestant.getPlayer().equals(TeleportTo)) {
                    TeleportToContestant = singleContestant;
                    secondCheck = true;
                }
            }

            // Check
            if (!firstCheck || TeleportPlayerContestant == null || !secondCheck || TeleportToContestant == null) return;

            // Check if both online
            if (Bukkit.getServer().getPlayer(TeleportPlayer) == null || Bukkit.getServer().getPlayer(TeleportTo) == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cÉén van de twee spelers is niet online!"));
                return;
            }

            // Check if they are both in map
            if (!Bukkit.getServer().getPlayer(TeleportPlayer).getWorld().getUID().equals(game.getMap()) || !Bukkit.getServer().getPlayer(TeleportTo).getWorld().getUID().equals(game.getMap())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cÉén van de twee spelers is niet in de map!"));
                return;
            }

            // Teleport
            Bukkit.getServer().getPlayer(TeleportPlayer).teleport(Bukkit.getServer().getPlayer(TeleportTo).getLocation());

            // Send message to everyone
            for (Player singlePlayer : Bukkit.getServer().getWorld(game.getMap()).getPlayers()) {
                singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + TeleportPlayerContestant.getChatColor() + TeleportPlayerContestant.getPlayername() + " &fis naar " + TeleportToContestant.getChatColor() + TeleportToContestant.getPlayername() + " &fgeteleporteerd!"));
            }

            // Remove player from TeleportChoices
            plugin.getGameManager().getTeleportChoice().remove(player.getUniqueId());
            player.closeInventory();
            // Take book
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            plugin.getGameManager().setTeleportChoice(player.getUniqueId(), clickHeadUUID);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aMaak nu een keuze naar wie je hem / haar wilt teleporteren!"));
            player.closeInventory();
            new Teleport().open(player);
        }
    }

    // PK Check
    protected void pkcheckMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;
        String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        // Get UUID
        UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
        if (clickHeadUUID == null) return;

        Contestant contestant = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(clickHeadUUID)) contestant = singleContestant;
        }

        if (contestant == null) return;

        String text = (contestant.getPeacekeeper()) ? "&2&lwel" : "&4&lniet";
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + contestant.getChatColor() + contestant.getPlayername() + " &fis " + text + " &fde &bPeacekeeper" ));
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
    }

    // Switch
    protected void switchMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;
        String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        // Get UUID
        UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
        if (clickHeadUUID == null) return;

        Contestant contestant = null;
        Contestant youContestant = null;

        for (Contestant singleContestant : game.getContestant()) {
            if (singleContestant.getPlayer() == null) continue;
            if (singleContestant.getPlayer().equals(clickHeadUUID)) contestant = singleContestant;
            if (singleContestant.getPlayer().equals(player.getUniqueId())) youContestant = singleContestant;
        }

        if (contestant == null || youContestant == null) return;

        // Check if the player is in the right world
        if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
            return;
        }

        Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

        if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
            return;
        }

        Location yourLocation = player.getLocation();
        Location otherLocation = otherPlayer.getLocation();

        player.teleport(otherLocation);
        otherPlayer.teleport(yourLocation);

        for (Player singlePlayer : player.getWorld().getPlayers()) {
            singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + youContestant.getChatColor() + youContestant.getColorName() + " &fis geswitched met " + contestant.getChatColor() + contestant.getColorName()));
        }

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
    }

    // Invsee
    protected void invseeMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;
        String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        // Get UUID
        UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
        if (clickHeadUUID == null) return;

        Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
        Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

        if (contestant == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
            return;
        }

        // Check if the player is in the right world
        if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
            return;
        }

        Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

        if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
            return;
        }

        player.closeInventory();
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        new InvseePlayer().openWithItems(player, Bukkit.getServer().getPlayer(contestant.getPlayer()).getInventory().getContents());

        for (Player singlePlayer : player.getWorld().getPlayers()) {
            singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + youContestant.getChatColor() + youContestant.getColorName() + " &fheeft een &d&lInvsee &fgebruikt op " + contestant.getChatColor() + contestant.getColorName()));
        }
    }

    // Invsee player
    protected void invseePlayerMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    // Itemcheck
    protected void itemcheckMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (clickedItem.getType().equals(Material.SKULL_ITEM)) {
            String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

            if (ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll(">> ", "").equalsIgnoreCase(" Kies het item hieronder!")) return;
            // Get UUID
            UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
            if (clickHeadUUID == null) return;

            Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
            Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

            if (contestant == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
                return;
            }

            // Check if the player is in the right world
            if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
                return;
            }

            Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

            if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
                return;
            }

            player.closeInventory();
            new ItemCheck().openPlayer(player, otherPlayer);
        } else {
            if (clickedItem.getType().equals(Material.DIAMOND_BLOCK) || clickedItem.getType().equals(Material.GOLD_BLOCK) || clickedItem.getType().equals(Material.BOOK)) {
                String clickedHead = ChatColor.stripColor(e.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).replaceAll(" >>", "");

                if (ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll(">> ", "").equalsIgnoreCase(" Kies het item hieronder!")) return;
                // Get UUID
                UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
                if (clickHeadUUID == null) return;

                Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
                Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

                if (contestant == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
                    return;
                }

                // Check if the player is in the right world
                if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
                    return;
                }

                Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

                if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
                    return;
                }

                int amount = 0;
                for (ItemStack item : Bukkit.getServer().getPlayer(contestant.getPlayer()).getInventory().getContents()) {
                    if (item == null || item.getType() == null) continue;

                    if (item.getType().equals(clickedItem.getType())) {
                        amount = amount + item.getAmount();
                    }
                }

                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                for (Player singlePlayer : player.getWorld().getPlayers()) {
                    singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + youContestant.getChatColor() + youContestant.getColorName() + " &fheeft " + contestant.getChatColor() + contestant.getColorName() + " &fop " + clickedItem.getItemMeta().getDisplayName() + " &fgecheckt en er zijn &7&l(&a&l" + amount + "&7&l) &faanwezig"));
                }

                player.closeInventory();
            }
        }
    }

    // Itemcheck
    protected void itemclearMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (clickedItem.getType().equals(Material.SKULL_ITEM)) {
            String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

            if (ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll(">> ", "").equalsIgnoreCase(" Kies het item hieronder!")) return;
            // Get UUID
            UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
            if (clickHeadUUID == null) return;

            Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
            Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

            if (contestant == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
                return;
            }

            // Check if the player is in the right world
            if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
                return;
            }

            Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

            if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
                return;
            }

            player.closeInventory();
            new ItemClear().openPlayer(player, otherPlayer);
        } else {
            if (clickedItem.getType().equals(Material.DIAMOND_BLOCK) || clickedItem.getType().equals(Material.GOLD_BLOCK) || clickedItem.getType().equals(Material.BOOK)) {
                String clickedHead = ChatColor.stripColor(e.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).replaceAll(" >>", "");

                if (ChatColor.stripColor(clickedItem.getItemMeta().getLore().get(0)).replaceAll(">> ", "").equalsIgnoreCase(" Kies het item hieronder!")) return;
                // Get UUID
                UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
                if (clickHeadUUID == null) return;

                Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
                Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

                if (contestant == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
                    return;
                }

                // Check if the player is in the right world
                if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
                    return;
                }

                Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

                if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
                    return;
                }

                String materialItems = null;
                if (!clickedItem.getType().equals(Material.DIAMOND_BLOCK) && !clickedItem.getType().equals(Material.DIAMOND_SWORD) && !clickedItem.getType().equals(Material.BOOK)) {
                    return;
                }

                if (clickedItem.getType().equals(Material.DIAMOND_BLOCK)) {
                    materialItems = "Block(s)";

                    for (ItemStack item : otherPlayer.getInventory().getContents()) {
                        if (item == null || item.getType() == null) continue;
                        if (item.getType().equals(Material.DIAMOND_BLOCK) ||
                            item.getType().equals(Material.GOLD_BLOCK) ||
                            item.getType().equals(Material.EMERALD_BLOCK) ||
                            item.getType().equals(Material.OBSIDIAN) ||
                            item.getType().equals(Material.WEB) ||
                            item.getType().equals(Material.LADDER)) {
                            otherPlayer.getInventory().remove(item);
                        }
                    }
                }

                if (clickedItem.getType().equals(Material.DIAMOND_SWORD)) {
                    materialItems = "Wapen(s)";

                    for (ItemStack item : otherPlayer.getInventory().getContents()) {
                        if (item == null || item.getType() == null) continue;
                        if (item.getType().equals(Material.DIAMOND_SWORD) ||
                                item.getType().equals(Material.GOLD_SWORD) ||
                                item.getType().equals(Material.IRON_SWORD) ||
                                item.getType().equals(Material.STONE_SWORD) ||
                                item.getType().equals(Material.WOOD_SWORD) ||
                                item.getType().equals(Material.BOW) ||
                                item.getType().equals(Material.ARROW) ||
                                item.getType().equals(Material.TIPPED_ARROW) ||
                                item.getType().equals(Material.SPECTRAL_ARROW) ||
                                item.getType().equals(Material.INK_SACK) ||
                                item.getType().equals(Material.STICK)) {
                            otherPlayer.getInventory().remove(item);
                        }
                    }
                }

                if (clickedItem.getType().equals(Material.BOOK)) {
                    materialItems = "Book(s)";

                    for (ItemStack item : otherPlayer.getInventory().getContents()) {
                        if (item == null || item.getType() == null) continue;
                        if (item.getType().equals(Material.BOOK)) {
                            otherPlayer.getInventory().remove(item);
                        }
                    }
                }

                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                for (Player singlePlayer : player.getWorld().getPlayers()) {
                    singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + youContestant.getChatColor() + youContestant.getColorName() + " &fheeft " + contestant.getChatColor() + contestant.getColorName() + " &fgecleared op " + materialItems));
                }

                player.closeInventory();
            }
        }
    }

    // Booklock
    protected void booklockMenuInteraction(InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == null) return;

        if (!(e.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        Player player = (Player) e.getWhoClicked();

        // Check if you are in a game
        if (!plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) return;
        Game game = (plugin.getGameDataManager().alreadyContestant(player.getUniqueId())) ? plugin.getGameDataManager().getContestingGame(player.getUniqueId()) : null;

        if (e.getInventory().getItem(e.getSlot()) == null) return;
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
        // Stained glass or barrier
        if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) return;

        // Check if its an head
        if (!clickedItem.getType().equals(Material.SKULL_ITEM)) return;
        String clickedHead = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replaceAll(" >>", "");

        // Get UUID
        UUID clickHeadUUID = (Bukkit.getServer().getPlayer(clickedHead) != null) ? Bukkit.getServer().getPlayer(clickedHead).getUniqueId() : Bukkit.getServer().getOfflinePlayer(clickedHead).getUniqueId();
        if (clickHeadUUID == null) return;

        Contestant youContestant = (game.isContestant(player.getUniqueId())) ? game.getContestant(player.getUniqueId()) : null;
        Contestant contestant = (game.isContestant(clickHeadUUID)) ? game.getContestant(clickHeadUUID) : null;

        if (contestant == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler zit niet in het spel"));
            return;
        }

        // Check if the player is in the right world
        if (Bukkit.getServer().getPlayer(contestant.getPlayer()) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet online!"));
            return;
        }

        Player otherPlayer = Bukkit.getServer().getPlayer(contestant.getPlayer());

        if (!otherPlayer.getWorld().getUID().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cDe gekozen speler is niet in de juiste wereld!"));
            return;
        }

        contestant.setBooklock(true);
        game.updateContestant(contestant);
        player.closeInventory();
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        for (Player singlePlayer : player.getWorld().getPlayers()) {
            singlePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + youContestant.getChatColor() + youContestant.getColorName() + " &fheeft een &7&lBooklock &fgebruikt op " + contestant.getChatColor() + contestant.getColorName()));
        }
    }
}
