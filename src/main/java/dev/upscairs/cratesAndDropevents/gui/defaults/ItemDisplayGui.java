package dev.upscairs.cratesAndDropevents.gui.defaults;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayGui implements InventoryGui, InventoryHolder {

    private Inventory inventory;

    private String title = "";


    public ItemDisplayGui() {
        setupInventory();
    }


    public void setupInventory() {
        inventory = Bukkit.createInventory((InventoryHolder) this, 54, title);
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    /**
     *
     * Sets the size of the inventory. Default size is 54.
     * Keeps the current items in the inventory.
     *
     * @param size
     */
    @Override
    public void setSize(int size) {

        if(size/9 <= 0 || size % 9 != 0 || size > 54) {
            size = 54;
        }

        InventoryHolder holder = inventory.getHolder();
        ItemStack[] items = inventory.getContents();

        inventory = Bukkit.createInventory(holder, size, title);
        setInventoryItems(items);

    }

    /**
     *
     * Sets the title of the inventory.
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {

        InventoryHolder holder = inventory.getHolder();
        ItemStack[] items = inventory.getContents();
        int size = inventory.getSize();

        this.title = title;

        inventory = Bukkit.createInventory(holder, size, title);
        setInventoryItems(items);
    }

    /**
     *
     * Sets the InventoryHolder of the inventory.
     * InventoryHolder should preferrably be the highest Instance of InventoryHolder in the hierarchy.
     *
     * @param holder
     */
    @Override
    public void setHolder(InventoryHolder holder) {

        ItemStack[] items = inventory.getContents();
        int size = inventory.getSize();

        inventory = Bukkit.createInventory(holder, size, title);
        setInventoryItems(items);

    }

    /**
     *
     * Places items into the inventory.
     *
     * @param items
     */
    private void setInventoryItems(ItemStack[] items) {
        for(int i = 0; i < items.length && i < inventory.getSize(); i++) {
            inventory.setItem(i, items[i]);
        }
    }

    /**
     *
     * Returns the inventory.
     *
     * @return
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
