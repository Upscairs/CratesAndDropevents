package dev.upscairs.cratesAndDropevents.gui.helper;

import org.bukkit.inventory.ItemStack;

public class ListableItemStack implements ListableGuiObject {

    private final ItemStack itemStack;

    public ListableItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack;
    }

    @Override
    public ItemStack getRenderItem() {
        return itemStack;
    }
}
