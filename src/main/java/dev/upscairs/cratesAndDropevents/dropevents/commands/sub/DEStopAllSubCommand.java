package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DEStopAllSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;

    public DEStopAllSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "stopall";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;

        DropEventManager.stopAll();
        sender.sendMessage(messageConfig.getColored("dropevent.success.all-stopped"));
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
