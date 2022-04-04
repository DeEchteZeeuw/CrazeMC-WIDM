package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class HostsMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Game game = (plugin.getGameDataManager().alreadyHosting(player.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(player.getUniqueId()) : null;

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden die jij host, het menu sluit!"));
            return;
        }

        if (!game.getMap().equals(player.getWorld().getUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld, het menu sluit!"));
            player.closeInventory();
            return;
        }

        ArrayList<Player> possibleHostLists = new ArrayList<>();
        ArrayList<UUID> hostsFromGame = game.getHosts();
        hostsFromGame.remove(0);

        int guisize = 9;
        if (possibleHostLists.size()+hostsFromGame.size() > 9) guisize = 18;
        if (possibleHostLists.size()+hostsFromGame.size() > 18) guisize = 27;
        if (possibleHostLists.size()+hostsFromGame.size() > 27) guisize = 36;
        if (possibleHostLists.size()+hostsFromGame.size() > 36) guisize = 45;
        if (possibleHostLists.size()+hostsFromGame.size() > 45) guisize = 54;

        for (Player singlePlayer : Bukkit.getServer().getOnlinePlayers()) {
            // Check if player has any host permission
            if (singlePlayer.hasPermission("crazemc.tempvuller") || singlePlayer.hasPermission("crazemc.jrgamehost") || singlePlayer.hasPermission("crazemc.gamehost") || singlePlayer.hasPermission("crazemc.srgamehost")) {
                if (!plugin.getGameDataManager().alreadyHosting(singlePlayer.getUniqueId()) && !plugin.getGameDataManager().alreadyContestant(singlePlayer.getUniqueId())) possibleHostLists.add(singlePlayer);
            }
        }

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                guisize,
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i< guisize;i++) {
            gui.setItem(i, this.background());
        }

        // Set possible hosts
        int lastnumber = 0;
        for (int i = 0; i < possibleHostLists.size();i++) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om hem/haar toe te voegen als host"));
            gui.setItem(i, this.headItem(player, lore));
            lastnumber = i;
        }
        // Set already hosts
        for (int i = 0; i < hostsFromGame.size();i++) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om hem/haar te verwijderen als host"));
            Player host = Bukkit.getServer().getPlayer(hostsFromGame.get(i));
            gui.setItem(lastnumber+i, this.headItem(host, lore));
        }

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik om terug te gaan"));
        if (possibleHostLists.size() + hostsFromGame.size() == 0) gui.setItem(4, this.menuItem("&c&lGeen speler(s) gevonden &8>>", "BARRIER", 1, 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().hostsTitle;
    }

    @Override
    public Integer size() {
        return 54;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().hostsBackgroundMaterial), plugin.getConfigManager().getGui().hostsBackgroundAmount, plugin.getConfigManager().getGui().hostsBackgroundShort);
        ItemMeta MetaBackgroundItem = BackgroundItem.getItemMeta();
        MetaBackgroundItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        BackgroundItem.setItemMeta(MetaBackgroundItem);
        MetaBackgroundItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', " "));
        BackgroundItem.setItemMeta(MetaBackgroundItem);
        return BackgroundItem;
    }

    @Override
    public ItemStack menuItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack headItem(Player player, ArrayList<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skull.setItemMeta(skullMeta);
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l"+player.getName()+" &8>>"));
        itemMeta.setLore(lore);
        skull.setItemMeta(itemMeta);
        return skull;
    }
}
