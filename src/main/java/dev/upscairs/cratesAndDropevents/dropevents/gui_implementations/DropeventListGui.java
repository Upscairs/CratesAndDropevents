package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.PageGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DropeventListGui {

    List<Dropevent> dropevents;
    CommandSender sender;

    private PageGui gui;

    public DropeventListGui(CommandSender sender) {

        this.dropevents = DropeventStorage.getAll();

        gui = new PageGui(new InteractableGui(new ItemDisplayGui()), dropevents, 0);
        configureClickReaction();

        this.sender = sender;
        gui.showPageInTitle(true);
        gui.setTitle("All Dropevents");
    }


    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot >= 0 && slot <= 44) {
                int selectedIndex = slot+45*gui.getPage();

                if(dropevents.size() <= selectedIndex) {
                    return new PreventCloseGui();
                }

                Bukkit.dispatchCommand(sender, "dropevent info " + dropevents.get(selectedIndex).getName());
                return new PreventCloseGui();

            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }


}
