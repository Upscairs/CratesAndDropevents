package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.cratesAndDropevents.gui.defaults.InteractableGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.InventoryGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.ItemDisplayGui;
import dev.upscairs.cratesAndDropevents.gui.defaults.PlayerInventoryClickReacting;
import dev.upscairs.cratesAndDropevents.gui.functional.InvGuiUtils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class SingleDropGui extends InteractableGui implements InventoryHolder, PlayerInventoryClickReacting {

    private Dropevent dropevent;
    private ItemStack item;
    private boolean itemSelection;
    private CommandSender sender;
    private Plugin plugin;
    private int unusedChance;

    public SingleDropGui(Dropevent dropevent, ItemStack item, boolean itemSelection, int unusedChance, CommandSender sender, Plugin plugin) {

        super(new ItemDisplayGui());

        this.dropevent = dropevent;
        this.item = item;
        this.itemSelection = itemSelection;
        this.sender = sender;
        this.plugin = plugin;
        this.unusedChance = unusedChance;

        setHolder(this);
        setSize(54);
        setTitle("Configure Loot for " + dropevent.getName());

        placeItems();
    }

    @Override
    public void placeItems() {

        ItemMeta meta;

        ItemStack backItem = new ItemStack(Material.ARROW);
        meta = backItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("To the overview", "#AAAAAA").decoration(TextDecoration.BOLD, true));
        backItem.setItemMeta(meta);
        setItem(45, backItem);


        setItem(13, generateDropItem());

        ItemStack chanceItem = new ItemStack(Material.CHEST);
        meta = chanceItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Probability: " + (float)(dropevent.getDrops().get(item))/10f + "%", "#FFAA00").decoration(TextDecoration.BOLD, true));
        chanceItem.setItemMeta(meta);
        setItem(29, chanceItem);

        ItemStack deleteItem = new ItemStack(Material.BARRIER);
        meta = deleteItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Delete Drop", "#FF5555").decoration(TextDecoration.BOLD, true));
        deleteItem.setItemMeta(meta);
        setItem(33, deleteItem);


    }

    private ItemStack generateDropItem() {

        ItemStack dropItem;
        ItemMeta meta;

        if(itemSelection) {
            dropItem = new ItemStack(Material.SCAFFOLDING);
            meta = dropItem.getItemMeta();
            meta.displayName(InvGuiUtils.generateDefaultTextComponent("Click on new item or click here to abort", "#AA00AA").decoration(TextDecoration.BOLD, true));
            meta.setEnchantmentGlintOverride(true);
            dropItem.setItemMeta(meta);
        }
        else {
            dropItem = item.clone();
            meta = dropItem.getItemMeta();
            meta.displayName(InvGuiUtils.generateDefaultTextComponent("Click to configure drop", "#AA00AA").decoration(TextDecoration.BOLD, true));
            dropItem.setItemMeta(meta);
        }

        return dropItem;

    }

    @Override
    public InventoryGui handleInvClick(int slot) {


        switch (slot) {
            case 13: return new SingleDropGui(dropevent, item, !itemSelection, unusedChance, sender, plugin);
            case 29: return new DropChanceSelectionGui(dropevent, item, dropevent.getDrops().get(item), unusedChance, sender, plugin);
            case 33:
                dropevent.removeDrop(item);
                DropeventStorage.saveDropevent(dropevent);
                return new DropeventDropsGui(dropevent, sender, plugin);
            case 45: return new DropeventDropsGui(dropevent, sender, plugin);
        }



        return super.handleInvClick(slot);

    }

    @Override
    public PlayerInventoryClickReacting onItemClick(ItemStack selectedItem) {

        Integer itemProb = dropevent.getDrops().get(item);

        dropevent.removeDrop(item);
        dropevent.setItemDropChance(selectedItem, itemProb);

        return new SingleDropGui(dropevent, selectedItem, false, unusedChance, sender, plugin);



    }



}
