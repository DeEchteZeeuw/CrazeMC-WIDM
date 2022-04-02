package io.github.deechtezeeuw.crazemcwidm.commands.itemlock;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUndroppable extends Commands {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void onCommand(CommandSender sender, Command command, String[] args) {
        // Check if user has host perms
        if (!sender.hasPermission("crazemc.tempvuller") && !sender.hasPermission("crazemc.jrgamehost") && !sender.hasPermission("crazemc.gamehost") && !sender.hasPermission("crazemc.srgamehost")) {
            plugin.getCommandManager().noPermission(null, sender);
            return;
        }
        // Check if user is in-game
        if (!(sender instanceof Player)) {
            plugin.getCommandManager().onlyPlayer(sender);
            return;
        }

        Player player = (Player) sender;

        if (!plugin.getSQL().sqlSelect.playerIsHost(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
            return;
        }

        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        if (!player.getWorld().getUID().equals(game.getMap())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe bent niet in de juiste wereld om dit te doen."));
            return;
        }

        // Check if player has item in hand
        if (player.getInventory().getItemInMainHand() == null) {
            player.sendMessage("Je moet een item in je hand hebben hiervoor!");
            return;
        }

        // Item
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getItemMeta() == null ) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe hebt geen item in je hand."));
            return;
        }

        try {
            ItemMeta itemMeta = item.getItemMeta();
            List<String> itemLore = (itemMeta.getLore() == null) ? new ArrayList<String>() : itemMeta.getLore();
            String text = ChatColor.translateAlternateColorCodes('&', "&8>> &c&lUndroppable");
            itemLore.add(text);
            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cEr ging iets mis!"));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&aItem is undroppable gemaakt!"));
    }

    @Override
    public String name() {
        return "undroppable";
    }

    @Override
    public String info() {
        return "make the item undroppable";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
