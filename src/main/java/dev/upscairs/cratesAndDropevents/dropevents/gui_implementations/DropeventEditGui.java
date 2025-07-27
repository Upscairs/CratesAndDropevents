package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.McGuiFramework;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.gui_wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class DropeventEditGui {

    private Dropevent dropevent;
    private CommandSender sender;
    private Plugin plugin;
    private boolean renderItemSelection;

    private InteractableGui gui;

    public DropeventEditGui(Dropevent dropevent, boolean renderItemSelection, CommandSender sender, Plugin plugin) {
        gui = new InteractableGui(new ItemDisplayGui());
        configureClickReaction();

        this.dropevent = dropevent;
        this.sender = sender;
        this.plugin = plugin;
        this.renderItemSelection = renderItemSelection;

        gui.setTitle("Edit " + dropevent.getName());
        gui.setSize(54);

        placeItems();
    }

    public void placeItems() {

        ItemMeta meta;

        ItemStack backItem = new ItemStack(Material.ARROW);
        meta = backItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("To the overview", "#AAAAAA").decoration(TextDecoration.BOLD, true));
        backItem.setItemMeta(meta);
        gui.setItem(45, backItem);

        ItemStack rangeItem = new ItemStack(Material.COMPASS);
        meta = rangeItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Range: " + dropevent.getDropRange(), "#55FFFF").decoration(TextDecoration.BOLD, true));
        rangeItem.setItemMeta(meta);
        gui.setItem(10, rangeItem);

        ItemStack timeItem = new ItemStack(Material.CLOCK);
        meta = timeItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Duration: " + dropevent.getEventTimeSec() + "s", "#55FF55").decoration(TextDecoration.BOLD, true));
        timeItem.setItemMeta(meta);
        gui.setItem(12, timeItem);


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
        gui.setItem(20, broadcastItem);


        ItemStack lootItem = new ItemStack(Material.CHEST);
        meta = lootItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Configure Loot Pool", "#FFAA00").decoration(TextDecoration.BOLD, true));
        lootItem.setItemMeta(meta);
        gui.setItem(31, lootItem);

        ItemStack droppedItem = new ItemStack(Material.HOPPER);
        meta = droppedItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Dropped Items: " + dropevent.getDropCount(), "#AA00AA").decoration(TextDecoration.BOLD, true));
        droppedItem.setItemMeta(meta);
        gui.setItem(14, droppedItem);

        ItemStack countdownItem = new ItemStack(Material.SPYGLASS);
        meta = countdownItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Countdown: " + dropevent.getCountdownSec() + "s", "#AA0000").decoration(TextDecoration.BOLD, true));
        countdownItem.setItemMeta(meta);
        gui.setItem(16, countdownItem);

        ItemStack startItem = new ItemStack(Material.FIREWORK_ROCKET);
        meta = startItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Start event here", "#00AA00").decoration(TextDecoration.BOLD, true));
        startItem.setItemMeta(meta);
        gui.setItem(49, startItem);

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
        gui.setItem(22, teleportItem);

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
        gui.setItem(24, renderItem);

        gui.placeItems();
    }

    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {

            if(slot < 54) {
                switch (slot) {
                    case 10:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new EditDropeventNumberGui(dropevent.getDropRange(), 0, 999, dropevent, "Range", sender).getGui();
                    case 12:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new EditDropeventNumberGui(dropevent.getEventTimeSec(), 1, 999, dropevent, "Duration", sender).getGui();
                    case 14:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new EditDropeventNumberGui(dropevent.getDropCount(), 1, 2500, dropevent, "Drops", sender).getGui();
                    case 16:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new EditDropeventNumberGui(dropevent.getCountdownSec(), 0, 999, dropevent, "Countdown", sender).getGui();
                    case 31:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new DropeventDropsGui(dropevent, sender, plugin).getGui();
                    case 45:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        Bukkit.dispatchCommand(sender, "dropevent list");
                        return new PreventCloseGui();
                    case 49:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        Bukkit.dispatchCommand(sender, "dropevent start " + dropevent.getName());
                        return null;
                    case 24:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        return new DropeventEditGui(dropevent, !renderItemSelection, sender, plugin).getGui();
                    case 20:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        dropevent.setBroadcasting(!dropevent.isBroadcasting());
                        DropeventStorage.saveDropevent(dropevent);
                        return new DropeventEditGui(dropevent, renderItemSelection, sender, plugin).getGui();
                    case 22:
                        if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                        dropevent.setTeleportable(!dropevent.isTeleportable());
                        DropeventStorage.saveDropevent(dropevent);
                        return new DropeventEditGui(dropevent, renderItemSelection, sender, plugin).getGui();
                    default:
                        return new PreventCloseGui();
                }
            }
            if(slot >= 54 && renderItemSelection) {

                if(item.getType().isAir()) {
                    return new PreventCloseGui();
                }

                dropevent.setRenderItem(item);
                DropeventStorage.saveDropevent(dropevent);

                if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                return new DropeventEditGui(dropevent, false, sender, plugin).getGui();
            }


            return new PreventCloseGui();
        });
    }

    public InteractableGui getGui() {
        return gui;
    }




}
