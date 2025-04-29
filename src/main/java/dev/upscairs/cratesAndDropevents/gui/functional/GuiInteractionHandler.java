package dev.upscairs.cratesAndDropevents.gui.functional;

import dev.upscairs.cratesAndDropevents.gui.defaults.InteractableGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.InventoryGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.PlayerInventoryClickReacting;
import dev.upscairs.cratesAndDropevents.gui.defaults.PreventCloseGui;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class GuiInteractionHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        //Prevents errors on console
        if (event.getClickedInventory() == null) {
            return;
        }

        //React to clicks on player inventory if Gui is reacting on this
        if(event.getView().getTopInventory().getHolder() instanceof PlayerInventoryClickReacting clickReactGui) {

            event.setCancelled(true);

            if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                    clickReactGui = clickReactGui.onItemClick(clickedItem.clone());

                    if(clickReactGui == null) {
                        event.getWhoClicked().closeInventory();
                    }

                    event.getWhoClicked().openInventory(clickReactGui.getInventory());
                }
            }
        }

        //React to clicks on guis
        if (event.getView().getTopInventory().getHolder() instanceof InventoryGui) {

            event.setCancelled(true);

            //Open sub-gui if possible
            if (event.getClickedInventory().getHolder() instanceof InteractableGui) {

                //If returned the same gui, it gets updated
                InventoryGui gui = ((InteractableGui) event.getClickedInventory().getHolder()).handleInvClick(event.getSlot());

                if(!(gui instanceof PreventCloseGui)) {
                    //Null closes inventory
                    if(gui == null) {
                        event.getWhoClicked().closeInventory();
                    }
                    else {
                        event.getWhoClicked().openInventory(gui.getInventory());
                    }
                }

            }

        }




    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        //Cancel interactions in guis
        if (event.getView().getTopInventory() instanceof InventoryGui) {
            event.setCancelled(true);
        }
    }

}