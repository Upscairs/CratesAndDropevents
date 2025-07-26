package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.NumberSelectionGui;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DropChanceSelectionGui {

    private Dropevent dropevent;
    private ItemStack drop;
    private int unusedChance;
    private CommandSender sender;
    private Plugin plugin;

    private NumberSelectionGui gui;

    public DropChanceSelectionGui(Dropevent dropevent, ItemStack changedDrop, int dropChance, int unusedChance, CommandSender sender, Plugin plugin) {

        gui = new NumberSelectionGui(new InteractableGui(new ItemDisplayGui()), dropChance, 0, dropChance+unusedChance, sender);
        configureClickReaction();

        this.dropevent = dropevent;
        this.drop = changedDrop;
        this.unusedChance = unusedChance;
        this.sender = sender;
        this.plugin = plugin;

        gui.setTitle("Drop chance in ‰");

    }

    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot == 30) {
                dropevent.setItemDropChance(drop, gui.getNumber());
                DropeventStorage.saveDropevent(dropevent);
                return new DropeventDropsGui(dropevent, sender, plugin).getGui();
            }
            else if(slot == 32) {
                return new SingleDropGui(dropevent, drop, false, unusedChance, sender, plugin).getGui();
            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }


}
