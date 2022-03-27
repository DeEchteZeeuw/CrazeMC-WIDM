package io.github.deechtezeeuw.crazemcwidm.gui.itemsSubs.weaponsSubs;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import io.github.deechtezeeuw.crazemcwidm.gui.GraphicalUserInterface;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class BowsMenu extends GraphicalUserInterface {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    @Override
    public void open(Player player) {
        Game game = plugin.getSQL().sqlSelect.playerHostGame(player.getUniqueId());

        if (game == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + "&cJe host geen game!"));
            return;
        }

        Inventory gui = Bukkit.getServer().createInventory(
                player,
                this.size(),
                ChatColor.translateAlternateColorCodes('&', this.title())
        );

        // Set background
        for (int i = 0; i< this.size();i++) {
            gui.setItem(i, this.background());
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om dit item te krijgen"));

        // Bows
        gui.setItem(11, this.bowItem("&d&lBow", "BOW", 1, 0, lore, 0));
        gui.setItem(20, this.bowItem("&d&lBow", "BOW", 1, 0, lore, 1));
        gui.setItem(29, this.bowItem("&d&lBow", "BOW", 1, 0, lore, 2));
        gui.setItem(38, this.bowItem("&d&lBow", "BOW", 1, 0, lore, 3));

        // Arrows
        gui.setItem(12, this.menuItem("&d&lArrows", "ARROW", 1,0, lore));
        gui.setItem(13, this.tippedArrowItem("&d&lNight Vision Arrows", "TIPPED_ARROW", 1, 0, lore, "NIGHT_VISION"));
        gui.setItem(14, this.tippedArrowItem("&d&lWeakness Arrows", "TIPPED_ARROW", 1, 0, lore, "WEAKNESS"));
        gui.setItem(15, this.tippedArrowItem("&d&lRegeneration Arrows", "TIPPED_ARROW", 1, 0, lore, "REGEN"));
        gui.setItem(21, this.tippedArrowItem("&d&lFire Resistance Arrows", "TIPPED_ARROW", 1, 0, lore, "FIRE_RESISTANCE"));
        gui.setItem(22, this.tippedArrowItem("&d&lInstant Damage Arrows", "TIPPED_ARROW", 1, 0, lore, "INSTANT_DAMAGE"));
        gui.setItem(23, this.tippedArrowItem("&d&lInstant Heal Arrows", "TIPPED_ARROW", 1, 0, lore, "INSTANT_HEAL"));
        gui.setItem(24, this.tippedArrowItem("&d&lPoison Arrows", "TIPPED_ARROW", 1, 0, lore, "POISON"));
        gui.setItem(30, this.tippedArrowItem("&d&lJump Arrows", "TIPPED_ARROW", 1, 0, lore, "JUMP"));
        gui.setItem(31, this.tippedArrowItem("&d&lInvisibility Arrows", "TIPPED_ARROW", 1, 0, lore, "INVISIBILITY"));
        gui.setItem(32, this.tippedArrowItem("&d&lSpeed Arrows", "TIPPED_ARROW", 1, 0, lore, "SPEED"));
        gui.setItem(33, this.tippedArrowItem("&d&lLuck Arrows", "TIPPED_ARROW", 1, 0, lore, "LUCK"));
        gui.setItem(39, this.tippedArrowItem("&d&lWater Breathing Arrows", "TIPPED_ARROW", 1, 0, lore, "WATER_BREATHING"));
        gui.setItem(40, this.tippedArrowItem("&d&lSlowness Arrows", "TIPPED_ARROW", 1, 0, lore, "SLOWNESS"));
        gui.setItem(41, this.tippedArrowItem("&d&lStrength Arrows", "TIPPED_ARROW", 1, 0, lore, "STRENGTH"));
        gui.setItem(42, this.menuItem("&d&lSpectral Arrows", "SPECTRAL_ARROW", 1, 0, lore));

        // Back to panel
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8>> &7Klik hier om terug te gaan naar het weapons menu"));
        gui.setItem(49, this.menuItem("&c&lTerug naar weapons &8>>", "BARRIER", 1 , 0, lore));

        player.openInventory(gui);
    }

    @Override
    public String title() {
        return plugin.getConfigManager().getMain().serverPrefix + plugin.getConfigManager().getMain().serverDivider + plugin.getConfigManager().getGui().bowsTitle;
    }

    @Override
    public Integer size() {
        return plugin.getConfigManager().getGui().bowsSize;
    }

    @Override
    public ItemStack background() {
        ItemStack BackgroundItem = new ItemStack(Material.valueOf(plugin.getConfigManager().getGui().bowsBackgroundMaterial), plugin.getConfigManager().getGui().bowsBackgroundAmount, plugin.getConfigManager().getGui().bowsBackgroundShort);
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

    public ItemStack bowItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore, int version) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        if (!material.equalsIgnoreCase("barrier")) {
            if (version == 1) {
                // unbreaking 1
                MetaItem.addEnchant(Enchantment.DURABILITY, 1, true);
            }
            if (version == 2) {
                // flame
                MetaItem.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            }
            if (version == 3) {
                // power 1
                MetaItem.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
            }
        }
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        return item;
    }

    public ItemStack tippedArrowItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore, String potionMeta) {
        ItemStack item = new ItemStack(Material.valueOf(material), amount, itemShort.shortValue());
        ItemMeta MetaItem = item.getItemMeta();
        MetaItem.setLore(lore);
        MetaItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(MetaItem);
        MetaItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        item.setItemMeta(MetaItem);
        PotionMeta tippedArrowMeta = (PotionMeta) item.getItemMeta();
        tippedArrowMeta.setBasePotionData(new PotionData(PotionType.valueOf(potionMeta)));
        item.setItemMeta(tippedArrowMeta);
        return item;
    }
}
