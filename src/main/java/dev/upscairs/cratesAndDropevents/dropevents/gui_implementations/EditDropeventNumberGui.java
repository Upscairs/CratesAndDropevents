package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.NumberSelectionGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class EditDropeventNumberGui {

    private Dropevent dropevent;
    private CommandSender sender;
    private String changedSetting;

    private NumberSelectionGui gui;

    public EditDropeventNumberGui(int startNumber, int minNumber, int maxNumber, Dropevent dropevent, String changedSetting, CommandSender sender) {

        gui = new NumberSelectionGui(new InteractableGui(new ItemDisplayGui()), startNumber, minNumber, maxNumber, sender);

        this.dropevent = dropevent;
        this.changedSetting = changedSetting;
        this.sender = sender;

        configureClickReaction();
        gui.setTitle("Edit " + changedSetting + " for " + dropevent.getName());
    }



    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot == 30) {
                Bukkit.dispatchCommand(sender, "dropevent edit " + dropevent.getName() + " "  + changedSetting + " " + gui.getNumber());
                Bukkit.dispatchCommand(sender, "dropevent info " + dropevent.getName());
            }
            else if(slot == 32) {
                Bukkit.dispatchCommand(sender, "dropevent info " + dropevent.getName());
            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }


}
