package io.github.deechtezeeuw.crazemcwidm.gui;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ColorPanel extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void openColor(Contestant contestant, Player host) {
        if (contestant == null) {
            host.closeInventory();
            host.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen speler gevonden met deze vereiste, het menu sluit"));
            new PanelMenu().open(host);
            return;
        }

        Game game = (plugin.getGameDataManager().alreadyHosting(host.getUniqueId())) ? plugin.getGameDataManager().getHostingGame(host.getUniqueId()) : null;

        if (game == null) {
            host.closeInventory();
            host.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr is geen game vonden die jij host, het menu sluit!"));
            new PanelMenu().open(host);
            return;
        }

        if (!game.getMap().equals(host.getWorld().getUID())) {
            host.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de correcte wereld, het menu sluit!"));
            host.closeInventory();
            new PanelMenu().open(host);
            return;
        }

        Inventory gui = Bukkit.getServer().createInventory(
                host,
                this.size(),
                ChatColor.translateAlternateColorCodes('&', this.title() + contestant.getChatColor() + "&lColor panel")
        );

        // Set background
        for (int i = 0; i < this.size();i++) {
            gui.setItem(i, this.background());
        }

        // Change player
        ArrayList<String> lore = new ArrayList<>();
        if (contestant.getPlayer() != null) {
            if (Bukkit.getServer().getPlayer(contestant.getPlayer()) != null) {
                // Set head
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &5&l" + Bukkit.getServer().getPlayer(contestant.getPlayer()).getName()));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de speler te verwijderen"));
                gui.setItem(10, this.headItem(Bukkit.getServer().getPlayer(contestant.getPlayer()), null, lore));
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &5&l" + Bukkit.getServer().getOfflinePlayer(contestant.getPlayer()).getName()));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de speler te verwijderen"));
                gui.setItem(10, this.headItem(null, Bukkit.getOfflinePlayer(contestant.getPlayer()), lore));
            }
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &c&lGeen speler"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om een speler toe te voegen"));
            gui.setItem(10, this.menuItem("&d&lSpeler &8>>", "SKULL_ITEM", 1, 0, lore));
        }

        // Change role
        lore = new ArrayList<>();
        switch (contestant.getRole()) {
            case 3:
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &b&lMol"));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de role aan te passen naar Onbekend"));
                gui.setItem(11, this.menuItem("&d&lRole &8>>", "COMPASS", 1, 0, lore));
                break;
            case 2:
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &b&lEgoïst"));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de role aan te passen naar Mol"));
                gui.setItem(11, this.menuItem("&d&lRole &8>>", "COMPASS", 1, 0, lore));
                break;
            case 1:
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &b&lSpeler"));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de role aan te passen naar Egoïst"));
                gui.setItem(11, this.menuItem("&d&lRole &8>>", "COMPASS", 1, 0, lore));
                break;
            default:
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &b&lOnbekend"));
                lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de role aan te passen naar Speler"));
                gui.setItem(11, this.menuItem("&d&lRole &8>>", "COMPASS", 1, 0, lore));
        }

        // Add or remove kill
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &5&l" + contestant.getKills()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik met linkermuisknop om een kill toe te voegen"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik met rechtermuisknop om een kill te verwijderen"));
        gui.setItem(12, this.menuItem("&d&lKill(s) &8>>", "IRON_SWORD", 1, 0, lore));

        // Change death
        lore = new ArrayList<>();
        if (contestant.getDeath()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &c&lDood"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de kleur tot leven te wekken"));
            gui.setItem(13, this.menuItem("&d&lDood &8>>", "POTION", 1, 0, lore));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &a&lLevend"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de kleur dood te maken"));
            gui.setItem(13, this.menuItem("&d&lDood &8>>", "POTION", 1, 0, lore));
        }

        // Change peacekeeper
        lore = new ArrayList<>();
        if (contestant.getPeacekeeper()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &b&lPeacekeeper"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om Peacekeeper uit te zetten"));
            gui.setItem(14, this.menuItem("&d&lPeacekeeper &8>>", "DIAMOND_HELMET", 1, 0, lore));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &c&lGeen Peacekeeper"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de Peacekeeper aan te zetten"));
            gui.setItem(14, this.menuItem("&d&lPeacekeeper &8>>", "DIAMOND_HELMET", 1, 0, lore));
        }

        // Add or remove PKkill
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &5&l" + contestant.getPeacekeeperKills()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik met linkermuisknop om een PK kill toe te voegen"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik met rechtermuisknop om een PK kill te verwijderen"));
        gui.setItem(15, this.menuItem("&d&lPK Kill(s) &8>>", "DIAMOND_SWORD", 1, 0, lore));

        // Contestant spawn
        lore = new ArrayList<>();
        if (contestant.getSpawn() != null) {
            Location location = contestant.getSpawn();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ()));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &f&lSpawn: &c&lNiet geset"));
        }
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om de spawn neer te zetten op deze locatie"));
        gui.setItem(16, this.menuItem("&d&lSpawn &8>>", contestant.getShulkerMaterial().toString(), 1, 0, lore));

        host.openInventory(gui);
    }

    @Override
    public void open(Player player) {
        return;
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider;
    }

    @Override
    public Integer size() {
        return 27;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 15);
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

    public ItemStack headItem(Player player, OfflinePlayer offlinePlayer, ArrayList<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (player != null) {
            skullMeta.setOwner(player.getName());
        } else {
            skullMeta.setOwningPlayer(offlinePlayer);
        }
        skull.setItemMeta(skullMeta);
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l"+(player == null ? offlinePlayer.getName() : player.getName())+" &8>>"));
        itemMeta.setLore(lore);
        skull.setItemMeta(itemMeta);
        return skull;
    }
}
