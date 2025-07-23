package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.gui_implementations.DropeventEditGui;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DEInfoSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;
    private final ChatMessageConfig messageConfig;

    public DEInfoSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "info";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;
        if(!(sender instanceof Player p)) return true;

        if(args.length == 1) {
            p.sendMessage(messageConfig.getColored("dropevent.error.missing-name"));
            return true;
        }

        Dropevent event = DropeventStorage.getDropeventByName(args[1]);

        if (event == null) {
            p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }

        DropeventEditGui editGui = new DropeventEditGui(event, false, sender, plugin);
        p.openInventory(editGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
