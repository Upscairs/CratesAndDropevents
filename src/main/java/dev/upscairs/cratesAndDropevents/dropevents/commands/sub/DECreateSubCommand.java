package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DECreateSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;

    public DECreateSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "create";
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

        if (args.length > 2) {
            p.sendMessage(messageConfig.getColored("dropevent.error.name-no-spaces"));
            return true;
        }

        String eventName = args[1];

        if (DropeventStorage.getDropeventByName(eventName) != null) {
            p.sendMessage(messageConfig.getColored("dropevent.error.name-already-exists"));
            return true;
        }

        Dropevent dropevent = new Dropevent(eventName);
        DropeventStorage.saveDropevent(dropevent);
        p.sendMessage(messageConfig.getColored("dropevent.success.created"));
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
