package io.github.deechtezeeuw.crazemcwidm.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class GraphicalUserInterface {

    public GraphicalUserInterface() {

    }

    public abstract void open(Player player);

    public abstract String title();
    public abstract Integer size();
    public abstract ItemStack background();
    public abstract ItemStack menuItem(String title, String material, Integer amount, Integer itemShort, ArrayList<String> lore);
}