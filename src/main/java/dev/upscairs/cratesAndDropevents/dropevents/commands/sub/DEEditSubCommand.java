package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DEEditSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;


    public DEEditSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "edit";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;

        if(args.length < 3) {
            sender.sendMessage(messageConfig.getColored("system.command.error.not-enough-arguments"));
            return  true;
        }

        String eventName = args[1];
        String setting = args[2];
        String value = args[3];

        Dropevent event = DropeventStorage.getDropeventByName(eventName);

        if (event == null) {
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }

        boolean success = event.changeSetting(setting, value);

        DropeventStorage.saveDropevent(event);

        if (success) {
            sender.sendMessage(messageConfig.getColored("dropevent.success.setting-changed"));
        } else {
            sender.sendMessage(messageConfig.getColored("dropevent.error.setting-update-failed"));
        }

        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
