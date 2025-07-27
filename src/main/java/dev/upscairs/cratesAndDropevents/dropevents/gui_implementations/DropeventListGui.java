package dev.upscairs.cratesAndDropevents.dropevents.gui_implementations;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CrateListGui;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.helper.ChatMessageInputHandler;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.McGuiFramework;
import dev.upscairs.mcGuiFramework.base.InventoryGui;
import dev.upscairs.mcGuiFramework.base.ItemDisplayGui;
import dev.upscairs.mcGuiFramework.functionality.PreventCloseGui;
import dev.upscairs.mcGuiFramework.gui_wrappers.InteractableGui;
import dev.upscairs.mcGuiFramework.gui_wrappers.PageGui;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.formatter.qual.InvalidFormat;

import java.util.List;

public class DropeventListGui {

    List<Dropevent> dropevents;
    CommandSender sender;

    private Plugin plugin;
    private ChatMessageConfig messageConfig;

    private PageGui gui;

    public DropeventListGui(CommandSender sender, Plugin plugin) {

        this.dropevents = DropeventStorage.getAll();

        gui = new PageGui(new InteractableGui(new ItemDisplayGui()), dropevents, 0);
        configureClickReaction();

        this.sender = sender;
        this.plugin = plugin;
        this.messageConfig = ((CratesAndDropevents) plugin).getChatMessageConfig();

        gui.showPageInTitle(true);
        gui.setTitle("All Dropevents");

        setItems();
    }

    public void setItems() {

        ItemStack createItem = new ItemStack(Material.CHEST_MINECART);
        ItemMeta meta = createItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent("Create new dropevent", "00AAAA"));
        createItem.setItemMeta(meta);
        gui.setItem(47, createItem);

    }


    private void configureClickReaction() {
        gui.onClick((slot, item, self) -> {
            if(slot >= 0 && slot <= 44) {
                int selectedIndex = slot+45*gui.getPage();

                if(dropevents.size() <= selectedIndex) {
                    return new PreventCloseGui();
                }

                if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                Bukkit.dispatchCommand(sender, "dropevent info " + dropevents.get(selectedIndex).getName());
                return new PreventCloseGui();

            }
            else if(slot == 47) {
                Component cancelComponent = Component.text(" [Cancel]", NamedTextColor.RED)
                        .clickEvent(ClickEvent.runCommand("/crates cancel"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to Cancel", NamedTextColor.RED)))
                        .decorate(TextDecoration.BOLD);

                sender.sendMessage(messageConfig.getColored("dropevent.info.type-name").append(cancelComponent));

                ChatMessageInputHandler.addListener(sender, (msg) -> {
                    if (sender instanceof Player p) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            Bukkit.dispatchCommand(sender, "dropevent create " + msg);
                            p.openInventory(new DropeventListGui(sender, plugin).getGui().getInventory());
                        });
                    }
                });

                if(sender instanceof Player p) p.closeInventory();
                if(sender instanceof Player p) McGuiFramework.getGuiSounds().playClickSound(p);
                return null;


            }

            return new PreventCloseGui();
        });
    }

    public InventoryGui getGui() {
        return gui;
    }


}
