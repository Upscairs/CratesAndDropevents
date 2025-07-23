package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import dev.upscairs.mcGuiFramework.utility.ListableGuiObject;
import dev.upscairs.mcGuiFramework.utility.ListableItemStack;
import dev.upscairs.mcGuiFramework.wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.wrappers.PageGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DropeventDropsGui {

    private Dropevent dropevent;
    private CommandSender sender;
    private Plugin plugin;

    private int unusedChance;

    private PageGui gui;

    public DropeventDropsGui(Dropevent dropevent, CommandSender sender, Plugin plugin) {
        gui = new PageGui(new InteractableGui(new ItemDisplayGui()),
                dropevent.getDrops().entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .map(ListableItemStack::new)
                    .toList(),
                0);
        configureClickReaction();

        this.dropevent = dropevent;
        this.sender = sender;
        this.plugin = plugin;

        gui.showPageInTitle(true);
        gui.setTitle("Loot Pool of " + dropevent.getName());

        placeItems();
        writeItemChances();


    }

    private void placeItems() {

        gui.placeItems();

        ItemMeta meta;

        ItemStack backItem = new ItemStack(Material.ARROW);
        meta = backItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("To edit window", "#AAAAAA").decoration(TextDecoration.BOLD, true));
        backItem.setItemMeta(meta);
        gui.setItem(46, backItem);

        ItemStack newDropItem = new ItemStack(Material.CHEST_MINECART);
        meta = newDropItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Add drop", "#FFAA00").decoration(TextDecoration.BOLD, true));
        newDropItem.setItemMeta(meta);
        gui.setItem(49, newDropItem);


    }


    /**
     *
     * Adds Meta text, which shows the chance of a drop to listed items.
     *
     */
    private void writeItemChances() {
        List<Map.Entry<ItemStack, Integer>> entries = new ArrayList<>(dropevent.getDrops().entrySet());
        //Sort by probability
        entries.sort(Map.Entry.<ItemStack, Integer>comparingByValue(Comparator.reverseOrder()));

        List<ListableItemStack> updated = new ArrayList<>();
        int summedProbability = 0;

        //Write chances
        for (Map.Entry<ItemStack, Integer> entry : entries) {
            ItemStack originalItem = entry.getKey();
            int itemChance = entry.getValue();
            summedProbability += itemChance;

            ItemStack item = originalItem.clone();
            List<Component> lore = List.of(
                    InvGuiUtils.generateDefaultTextComponent("Chance: " + (itemChance / 10f) + "%", "#55FFFF")
            );
            item.lore(new ArrayList<>(lore));

            updated.add(new ListableItemStack(item));
        }

        unusedChance = 1000 - summedProbability;


        //Create a "No drop" item if there is unused chance left.
        if (unusedChance > 0) {

            ItemStack voidItem = new ItemStack(Material.BARRIER);
            ItemMeta meta = voidItem.getItemMeta();
            meta.displayName(InvGuiUtils.generateDefaultTextComponent("No drop", "#FF5555").decoration(TextDecoration.BOLD, true));
            voidItem.setItemMeta(meta);
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(InvGuiUtils.generateDefaultTextComponent("Chance: " + ((float)unusedChance)/10f + "%", "#55FFFF"));
            voidItem.lore(lore);
            updated.add(new ListableItemStack(voidItem));

        }


        gui.setListedObjects(updated);
    }


    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {

            //Determine, which item was clicked on and if there is the non-clickable barrier item
            if(slot >= 0 && slot <= 44) {
                int selectedIndex = slot+45*gui.getPage();

                int lastAvailableIndex = gui.getListedObjects().size() - 1;

                if(unusedChance > 0f) {
                    lastAvailableIndex--;
                }

                if(lastAvailableIndex < selectedIndex) {
                    return new PreventCloseGui();
                }

                List<ListableGuiObject> listedObjects = gui.getListedObjects();
                ItemStack drop = listedObjects.get(selectedIndex).getRenderItem().clone();

                List<Component> lore = drop.lore();
                lore.remove(lore.size()-1);
                drop.lore(lore);


                return new SingleDropGui(dropevent, drop, false, unusedChance, sender, plugin).getGui();

            }

            //Return to DropeventEditGui
            if(slot == 46) {
                return new DropeventEditGui(dropevent, false, sender, plugin).getGui();
            }
            //Add a new drop to the Dropevent
            else if(slot == 49) {

                ItemStack newItem = new ItemStack(Material.STONE);
                ItemMeta meta = newItem.getItemMeta();
                meta.displayName(Component.text()
                        .content("Stone " + (dropevent.getDrops().size()+1))
                        .decoration(TextDecoration.ITALIC, false)
                        .build());
                newItem.setItemMeta(meta);

                if(unusedChance > 0f) {
                    dropevent.setItemDropChance(newItem, unusedChance);
                }
                else {
                    dropevent.setItemDropChance(newItem, 0);
                }

                DropeventStorage.saveDropevent(dropevent);

                return new DropeventDropsGui(dropevent, sender, plugin).getGui();
            }

            return gui;
        });
    }

    public InventoryGui getGui() {
        return gui;
    }

}
