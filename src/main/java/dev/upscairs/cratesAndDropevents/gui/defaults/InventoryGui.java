package dev.upscairs.cratesAndDropevents.gui.defaults;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface InventoryGui {

    void setupInventory();

    void setItem(int slot, ItemStack item);

    void setSize(int size);

    void setTitle(String title);

    void setHolder(InventoryHolder holder);

    Inventory getInventory();

}
