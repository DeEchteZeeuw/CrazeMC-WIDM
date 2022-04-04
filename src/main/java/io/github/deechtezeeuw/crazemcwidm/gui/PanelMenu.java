package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PanelMenu extends GraphicalUserInterface {
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

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                (game.getContestant().size() > 7) ? this.size() : this.size()-9,
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i < (game.getContestant().size() > 7 ? this.size() : this.size()-9);i++) {
            gui.setItem(i, this.background());
        }

        ArrayList<String> lore = new ArrayList<>();
        // Game status
        switch (game.getGameStatus()) {
            case 0:
                // Game is filling
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de game te starten"));
                gui.setItem(10, this.menuItem("&a&lStart Game &8>>", "WOOL", 1, 5, lore));
                break;
            case 1:
                // Game is playing
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de game te pauzeren"));
                gui.setItem(10, this.menuItem("&5&lPauzeer game &8>>", "WOOL", 1, 9, lore));
                break;
            case 2:
                // Game is paused
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de game te hervatten"));
                gui.setItem(10, this.menuItem("&a&lHervat Game &8>>", "WOOL", 1, 5, lore));
                break;
            default:
                // Game is ended
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de game volledig te sluiten"));
                gui.setItem(10, this.menuItem("&c&lSluit game &8>>", "WOOL", 1, 14, lore));
                break;
        }

        // Hosts menu
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om host(s) aan te passen"));
        gui.setItem(12, this.menuItem("&d&lHost(s) &8>>", "SKULL_ITEM", 1, 0, lore));

        // Items
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om het items menu te bekijken"));
        gui.setItem(14, this.menuItem("&d&lItems menu &8>>", "WORKBENCH", 1, 0, lore));

        if (game.getGameStatus() != 0) {
            // Vote reset
            lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de votes te resetten"));
            gui.setItem(16, this.menuItem("&d&lReset votes &8>>", "NOTE_BLOCK", 1, 0, lore));
        } else {
            // Unclaim game
            lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de game te unclaimen"));
            gui.setItem(16, this.menuItem("&e&lUnclaim &8>>", "WOOL", 1, 4, lore));
        }
        // Color statussen
        lore = new ArrayList<>();
        int place = 28;
        for (int i = 0; i < game.getContestant().size();i++) {
            if (place==35) place = place+2;
            Contestant contestant = game.getContestant().get(i);
            lore = new ArrayList<>();
            // Contestant playername
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpeler: " + (contestant.getPlayername().equalsIgnoreCase("Leeg") ? "&c&lLeeg" : contestant.getPlayername() )));
            // Contestant role
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lRol: &b&l" + contestant.getRoleName()));
            // Contestant kills
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lKill(s): " + contestant.getKills()));
            // Contestant death
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lDood: " + (contestant.getDeath() ? "&2&l✔" : "&4&l✘")));
            // Contestant peacekeeper
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lPeacekeeper: " + (contestant.getPeacekeeper() ? "&2&l✔" : "&4&l✘")));
            // Contestant peacekeeper kills
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lPKKill(s): " + contestant.getPeacekeeperKills()));
            // Contestant spawn
            if (contestant.getSpawn() != null) {
                Location location = contestant.getSpawn();
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ()));
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: &c&lNiet neergezet"));
            }
            // Click on me to open color menu
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7&lKlik om " + contestant.getChatColor() + "&l" + contestant.getColorName() + " &7&laan te passen"));
            gui.setItem(place, this.menuItem(contestant.getChatColor()+"&l"+contestant.getColorName()+" &8>>", contestant.getShulkerMaterial().toString(), 1, 0, lore));
            place++;
        }

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().panelTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().panelSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().panelBackgroundMaterial), plugin.getConfigManager().getGui().panelBackgroundAmount, plugin.getConfigManager().getGui().panelBackgroundShort);
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
}
