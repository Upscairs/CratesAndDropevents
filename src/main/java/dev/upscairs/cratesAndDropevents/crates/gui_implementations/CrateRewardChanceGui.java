package dev.upscairs.cratesAndDropevents.crates.gui_implementations;

import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.helper.EditMode;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.NumberSelectionGui;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CrateRewardChanceGui {

    private Crate crate;
    private CrateReward reward;
    private CommandSender sender;
    private Plugin plugin;

    private NumberSelectionGui gui;

    public CrateRewardChanceGui(int dropChance, int unusedChance, Crate crate, CrateReward reward, CommandSender sender, Plugin plugin) {

        gui = new NumberSelectionGui(new InteractableGui(new ItemDisplayGui()), dropChance, 0, dropChance+unusedChance, sender);

        this.crate = crate;
        this.reward = reward;
        this.sender = sender;
        this.plugin = plugin;

        configureClickReaction();
        gui.setTitle("Edit chance for reward (in ‰)");

    }

    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot == 30) {
                int newProb = gui.getNumber();
                crate.setRewardChance(reward, newProb);
                CrateStorage.saveCrate(crate);

                return new SingleRewardGui(crate, reward, null, EditMode.NONE, sender, plugin).getGui();
            }
            else if(slot == 32) {
                return new SingleRewardGui(crate, reward, null, EditMode.NONE, sender, plugin).getGui();
            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }
}
