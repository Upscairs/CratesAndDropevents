package dev.upscairs.cratesAndDropevents.helper;

import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.gui_wrappers.InteractableGui;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ConfirmationGui {

    private final InteractableGui gui;

    public ConfirmationGui(String title, ItemStack acceptItem, ItemStack cancelItem, Supplier<InventoryGui> acceptAction, Supplier<InventoryGui> cancelAction) {

        gui = new InteractableGui(new ItemDisplayGui());

        gui.setSize(9);
        gui.setTitle(title);

        gui.setItem(2, acceptItem);
        gui.setItem(6, cancelItem);

        configureClickReaction(acceptAction, cancelAction);
    }

    private void configureClickReaction(Supplier<InventoryGui> acceptAction, Supplier<InventoryGui> cancelAction) {

        gui.onClick((slot, item, self) -> {

            if(slot == 2) return acceptAction.get();
            else if(slot == 6) return cancelAction.get();

            return new PreventCloseGui();
        });
    }

    public InteractableGui getGui() {
        return gui;
    }

}
