package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.ScrollableGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class DropeventListGui extends ScrollableGui implements InventoryHolder {

    List<Dropevent> dropevents;
    CommandSender sender;

    public DropeventListGui(CommandSender sender) {
        super(new InteractableGui(new ItemDisplayGui()), DropeventStorage.getAll(), 0);
        this.dropevents = DropeventStorage.getAll();

        this.sender = sender;
        showPageInTitle(true);
        setTitle("All Dropevents");
        setHolder(this);
    }

    @Override
    public InventoryGui handleInvClick(int slot) {

        if(slot >= 0 && slot <= 44) {
            int selectedIndex = slot+45*getPage();

            if(dropevents.size() <= selectedIndex) {
                return new PreventCloseGui();
            }

            Bukkit.dispatchCommand(sender, "dropevent info " + dropevents.get(selectedIndex).getName());
            return new PreventCloseGui();

        }

        return super.handleInvClick(slot);

    }


}
