package dev.upscairs.cratesAndDropevents.gui.defaults;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GuiDecorator implements InventoryGui {

    private InventoryGui inventoryGui;

    public GuiDecorator(InventoryGui inventoryGui) {
        this.inventoryGui = inventoryGui;
    }

    @Override
    public void setupInventory() {
        inventoryGui.setupInventory();
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        inventoryGui.setItem(slot, item);
    }

    @Override
    public void setSize(int size) {
        inventoryGui.setSize(size);
    }

    @Override
    public void setTitle(String title) {
        inventoryGui.setTitle(title);
    }

    @Override
    public void setHolder(InventoryHolder holder) {
        inventoryGui.setHolder(holder);
    }

    @Override
    public Inventory getInventory() {
        return inventoryGui.getInventory();
    }
}
