package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class DEGiveSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;
    private final ChatMessageConfig messageConfig;

    public DEGiveSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "give";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;

        //Retrieve and check arguments
        Player target = plugin.getServer().getPlayer(args[1]);
        if(target == null) {
            sender.sendMessage(messageConfig.getColored("system.command.error.player-not-found"));
            return true;
        }


        Dropevent dropevent = DropeventStorage.getDropeventByName(args[2]);
        if(dropevent == null) {
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }


        int count = 1;
        if(args.length > 3) {
            try {
                count = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(messageConfig.getColored("system.command.error.invalid-number"));
                return true;
            }
        }
        if(count < 1 || count > 64) {
            sender.sendMessage(messageConfig.getColored("system.command.error.number-range-item"));
        }

        //Set values
        ItemStack givenItem = dropevent.getRenderItem();
        givenItem.setAmount(count);

        //Flag item as dropevent starter
        NamespacedKey key = ((CratesAndDropevents) plugin).EVENT_KEY;

        ItemMeta meta = givenItem.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, dropevent.getName());
        List<Component> lore = meta.lore();

        //Subtext
        if(lore == null) lore = new ArrayList<>();

        lore.add(InvGuiUtils.generateDefaultHeaderComponent("", "#000000"));
        lore.add(InvGuiUtils.generateDefaultHeaderComponent("Dropevent", "#A40064"));
        lore.add(InvGuiUtils.generateDefaultTextComponent("Shift + Right Click to start", "#AAAAAA"));

        meta.lore(lore);
        givenItem.setItemMeta(meta);

        //Give
        target.getInventory().addItem(givenItem);
        sender.sendMessage(messageConfig.getColored("dropevent.success.given"));

        return true;

    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
