package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DERemoveSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;

    public DERemoveSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "remove";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;

        if(args.length == 1) {
            sender.sendMessage(messageConfig.getColored("dropevent.error.missing-name"));
            return true;
        }

        Dropevent dropevent = DropeventStorage.getDropeventByName(args[1]);

        if (dropevent == null) {
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }

        DropeventStorage.removeDropevent(dropevent);
        sender.sendMessage(messageConfig.getColored("dropevent.success.removed"));

        return true;

    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
