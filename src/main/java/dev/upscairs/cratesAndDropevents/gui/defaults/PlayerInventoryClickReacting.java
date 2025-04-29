package dev.upscairs.cratesAndDropevents.gui.defaults;

import org.bukkit.inventory.ItemStack;

public interface PlayerInventoryClickReacting extends InventoryGui {

    PlayerInventoryClickReacting onItemClick(ItemStack selectedItem);

}
