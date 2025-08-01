package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventRunner;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DEStartNowSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;
    private final ChatMessageConfig messageConfig;

    public DEStartNowSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "start-now";
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
        der.startInstantly(locationName);
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
