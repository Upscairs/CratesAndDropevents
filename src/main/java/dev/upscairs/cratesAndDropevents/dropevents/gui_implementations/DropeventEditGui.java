package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.cratesAndDropevents.gui.defaults.*;
import dev.upscairs.cratesAndDropevents.gui.functional.InvGuiUtils;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class DropeventEditGui extends InteractableGui implements InventoryHolder, PlayerInventoryClickReacting {

    private Dropevent dropevent;
    private CommandSender sender;
    private Plugin plugin;
    private boolean renderItemSelection;

    public DropeventEditGui(Dropevent dropevent, boolean renderItemSelection, CommandSender sender, Plugin plugin) {
        super(new InteractableGui(new ItemDisplayGui()));
        this.dropevent = dropevent;
        this.sender = sender;
        this.plugin = plugin;
        this.renderItemSelection = renderItemSelection;

        setTitle("Edit " + dropevent.getName());
        setHolder(this);
        setSize(54);

        placeItems();
    }

    public void placeItems() {

        ItemMeta meta;

        ItemStack backItem = new ItemStack(Material.ARROW);
        meta = backItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("To the overview", "#AAAAAA").decoration(TextDecoration.BOLD, true));
        backItem.setItemMeta(meta);
        setItem(45, backItem);

        ItemStack rangeItem = new ItemStack(Material.COMPASS);
        meta = rangeItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Range: " + dropevent.getDropRange(), "#55FFFF").decoration(TextDecoration.BOLD, true));
        rangeItem.setItemMeta(meta);
        setItem(10, rangeItem);

        ItemStack timeItem = new ItemStack(Material.CLOCK);
        meta = timeItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Duration: " + dropevent.getEventTimeSec() + "s", "#55FF55").decoration(TextDecoration.BOLD, true));
        timeItem.setItemMeta(meta);
        setItem(12, timeItem);


        ItemStack broadcastItem = new ItemStack(Material.BELL);
        meta = broadcastItem.getItemMeta();

        TextComponent broadcastBooleanComponent = dropevent.isBroadcasting() ?
                InvGuiUtils.generateDefaultTextComponent("on", "#55FF55")
                : InvGuiUtils.generateDefaultTextComponent("off", "#FF5555");

        meta.displayName(InvGuiUtils
                .generateDefaultTextComponent("Broadcasting: ", "#CCCCCC")
                .append(broadcastBooleanComponent)
                .decoration(TextDecoration.BOLD, true));
        meta.setEnchantmentGlintOverride(dropevent.isBroadcasting());
        broadcastItem.setItemMeta(meta);
        setItem(20, broadcastItem);


        ItemStack lootItem = new ItemStack(Material.CHEST);
        meta = lootItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Configure Loot Pool", "#FFAA00").decoration(TextDecoration.BOLD, true));
        lootItem.setItemMeta(meta);
        setItem(31, lootItem);

        ItemStack droppedItem = new ItemStack(Material.HOPPER);
        meta = droppedItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Dropped Items: " + dropevent.getDropCount(), "#AA00AA").decoration(TextDecoration.BOLD, true));
        droppedItem.setItemMeta(meta);
        setItem(14, droppedItem);

        ItemStack countdownItem = new ItemStack(Material.SPYGLASS);
        meta = countdownItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Countdown: " + dropevent.getCountdownSec() + "s", "#AA0000").decoration(TextDecoration.BOLD, true));
        countdownItem.setItemMeta(meta);
        setItem(16, countdownItem);

        ItemStack startItem = new ItemStack(Material.FIREWORK_ROCKET);
        meta = startItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Start event here", "#00AA00").decoration(TextDecoration.BOLD, true));
        startItem.setItemMeta(meta);
        setItem(49, startItem);

        ItemStack teleportItem = new ItemStack(Material.ENDER_PEARL);
        meta = teleportItem.getItemMeta();
        TextComponent teleportBooleanComponent = dropevent.isTeleportable() ?
                InvGuiUtils.generateDefaultTextComponent("yes", "#55FF55")
                : InvGuiUtils.generateDefaultTextComponent("no", "#FF5555");

        meta.displayName(InvGuiUtils
                .generateDefaultTextComponent("Teleportable: ", "#009999")
                .append(teleportBooleanComponent)
                .decoration(TextDecoration.BOLD, true));
        meta.setEnchantmentGlintOverride(dropevent.isTeleportable());
        teleportItem.setItemMeta(meta);
        setItem(22, teleportItem);

        ItemStack renderItem;

        if(renderItemSelection) {
            renderItem = new ItemStack(Material.SCAFFOLDING);
            meta = renderItem.getItemMeta();
            meta.displayName(InvGuiUtils.generateDefaultTextComponent("Click on new item or click here to abort", "#AA00AA").decoration(TextDecoration.BOLD, true));
            meta.setEnchantmentGlintOverride(true);
            renderItem.setItemMeta(meta);
        }
        else {
            renderItem = dropevent.getRenderItem().clone();
            meta = renderItem.getItemMeta();
            meta.displayName(InvGuiUtils.generateDefaultTextComponent("Click to configure render item", "#AA00AA").decoration(TextDecoration.BOLD, true));
            renderItem.setItemMeta(meta);
        }
        setItem(24, renderItem);

        super.placeItems();
    }

    @Override
    public InventoryGui handleInvClick(int slot) {

        switch (slot) {
            case 10: return new EditDropeventNumberGui(dropevent.getDropRange(), 0, 999, dropevent, "Range", sender);
            case 12: return new EditDropeventNumberGui(dropevent.getEventTimeSec(), 1, 999, dropevent, "Duration", sender);
            case 14: return new EditDropeventNumberGui(dropevent.getDropCount(), 1, 2500, dropevent, "Drops", sender);
            case 16: return new EditDropeventNumberGui(dropevent.getCountdownSec(), 0, 999, dropevent, "Countdown", sender);
            case 31: return new DropeventDropsGui(dropevent, sender, plugin);
            case 45: Bukkit.dispatchCommand(sender, "dropevent list"); return new PreventCloseGui();
            case 49: Bukkit.dispatchCommand(sender, "dropevent start " + dropevent.getName()); return null;
            case 24: return new DropeventEditGui(dropevent, !renderItemSelection, sender, plugin);
            case 20:
                dropevent.setBroadcasting(!dropevent.isBroadcasting());
                DropeventStorage.saveDropevent(dropevent);
                return new DropeventEditGui(dropevent, renderItemSelection, sender, plugin);
            case 22:
                dropevent.setTeleportable(!dropevent.isTeleportable());
                DropeventStorage.saveDropevent(dropevent);
                return new DropeventEditGui(dropevent, renderItemSelection, sender, plugin);
            default: return super.handleInvClick(slot);
        }

    }

    @Override
    public PlayerInventoryClickReacting onItemClick(ItemStack selectedItem) {

        dropevent.setRenderItem(selectedItem);
        DropeventStorage.saveDropevent(dropevent);
        return new DropeventEditGui(dropevent, false, sender, plugin);

    }




}
