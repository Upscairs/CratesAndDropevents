package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.NumberSelectionGui;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DropChanceSelectionGui extends NumberSelectionGui implements InventoryHolder {

    private Dropevent dropevent;
    private ItemStack drop;
    private int unusedChance;
    private CommandSender sender;
    private Plugin plugin;

    public DropChanceSelectionGui(Dropevent dropevent, ItemStack changedDrop, int dropChance, int unusedChance, CommandSender sender, Plugin plugin) {

        super(new InteractableGui(new ItemDisplayGui()), dropChance, 0, dropChance+unusedChance, sender);

        this.dropevent = dropevent;
        this.drop = changedDrop;
        this.unusedChance = unusedChance;
        this.sender = sender;
        this.plugin = plugin;

        setHolder(this);
        setTitle("Drop chance in ‰");

    }

    /**
     *
     * Saves and throws back to DropeventDropsGui or aborts and throws back to SingleDropGui
     *
     * @param slot
     * @return
     */
    @Override
    public InventoryGui handleInvClick(int slot) {

        if(slot == 30) {
            dropevent.setItemDropChance(drop, getNumber());
            DropeventStorage.saveDropevent(dropevent);
            return new DropeventDropsGui(dropevent, sender, plugin);
        }
        else if(slot == 32) {
            return new SingleDropGui(dropevent, drop, false, unusedChance, sender, plugin);
        }

        return super.handleInvClick(slot);

    }


}
