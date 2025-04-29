package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.gui.defaults.InteractableGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.InventoryGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.ItemDisplayGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.NumberSelectionGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class EditDropeventNumberGui extends NumberSelectionGui {

    private Dropevent dropevent;
    private CommandSender sender;
    private String changedSetting;

    public EditDropeventNumberGui(int startNumber, int minNumber, int maxNumber, Dropevent dropevent, String changedSetting, CommandSender sender) {
        super(new InteractableGui(new ItemDisplayGui()), startNumber, minNumber, maxNumber, sender);
        this.dropevent = dropevent;
        this.changedSetting = changedSetting;
        this.sender = sender;
        setTitle("Edit " + changedSetting + " for " + dropevent.getName());
    }

    @Override
    public InventoryGui handleInvClick(int slot) {

        if(slot == 30) {
            Bukkit.dispatchCommand(sender, "dropevent edit " + dropevent.getName() + " "  + changedSetting + " " + getNumber());
            Bukkit.dispatchCommand(sender, "dropevent info " + dropevent.getName());
        }
        else if(slot == 32) {
            Bukkit.dispatchCommand(sender, "dropevent info " + dropevent.getName());
        }


        return super.handleInvClick(slot);

    }


}
