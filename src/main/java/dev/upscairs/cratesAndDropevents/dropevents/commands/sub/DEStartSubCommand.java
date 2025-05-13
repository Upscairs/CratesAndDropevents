package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventRunner;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DEStartSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;
    private final ChatMessageConfig messageConfig;

    public DEStartSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "start";
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

        String locationName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        if (event == null) {
            p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }

        DropEventRunner der = new DropEventRunner(event, p, plugin);
        der.startCountdown(locationName);
        return true;

    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
