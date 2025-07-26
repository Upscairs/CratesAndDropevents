package dev.upscairs.cratesAndDropevents.crates.gui_implementations;

import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.helper.EditMode;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import dev.upscairs.cratesAndDropevents.crates.rewards.payouts.DelayRewardEvent;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.NumberSelectionGui;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CrateRewardDelaySelection {

    private Crate crate;
    private CrateReward crateReward;
    private DelayRewardEvent delayEvent;
    private CommandSender sender;
    private Plugin plugin;

    private NumberSelectionGui gui;

    public CrateRewardDelaySelection(int currentDelay, CrateReward crateReward, Crate crate, DelayRewardEvent delayEvent, CommandSender sender, Plugin plugin) {

        gui = new NumberSelectionGui(new InteractableGui(new ItemDisplayGui()), currentDelay, 0, 1000, sender);

        this.crate = crate;
        this.crateReward = crateReward;
        this.delayEvent = delayEvent;
        this.sender = sender;
        this.plugin = plugin;

        configureClickReaction();
        gui.setTitle("Choose Delay in Ticks");

    }

    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot == 30) {
                int newDelay = gui.getNumber();
                delayEvent.setTicks(newDelay);
                CrateStorage.saveCrate(crate);
                return new SingleRewardGui(crate, crateReward, null, EditMode.NONE, sender, plugin).getGui();
            }
            else if(slot == 32) {
                return new SingleRewardGui(crate, crateReward, null, EditMode.NONE, sender, plugin).getGui();
            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }

}
