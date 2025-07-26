package dev.upscairs.cratesAndDropevents.crates.gui_implementations;

import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.PageGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrateListGui {

    List<Crate> crates;
    CommandSender sender;

    private PageGui gui;

    public CrateListGui(CommandSender sender) {
        this.crates = CrateStorage.getAll();

        gui = new PageGui(new InteractableGui(new ItemDisplayGui()), crates, 0);
        configureClickReaction();

        this.sender = sender;
        gui.showPageInTitle(true);
        gui.setTitle("All Crates");
    }


    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot >= 0 && slot <= 44) {
                int selectedIndex = slot+45*gui.getPage();

                if(crates.size() <= selectedIndex) {
                    return new PreventCloseGui();
                }

                Bukkit.dispatchCommand(sender, "crates info " + crates.get(selectedIndex).getName());
                return new PreventCloseGui();

            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }


}
