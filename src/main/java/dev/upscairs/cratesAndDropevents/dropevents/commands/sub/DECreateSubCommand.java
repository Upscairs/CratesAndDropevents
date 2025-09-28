package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import dev.upscairs.mcGuiFramework.McGuiFramework;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DECreateSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;

    public DECreateSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
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

        if(!isSenderPermitted(sender)) return true;

        ChatMessageConfig messageConfig = plugin.getChatMessageConfig();

        if(args.length == 1) {
            sender.sendMessage(messageConfig.getColored("dropevent.error.missing-name"));
            return true;
        }

        if (args.length > 2) {
            if(sender instanceof Player p) McGuiFramework.getGuiSounds().playFailSound(p);
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-no-spaces"));
            return true;
        }

        String eventName = args[1];

        if (DropeventStorage.getDropeventByName(eventName) != null) {
            if(sender instanceof Player p) McGuiFramework.getGuiSounds().playFailSound(p);
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-already-exists"));
            return true;
        }

        Dropevent dropevent = new Dropevent(eventName);
        DropeventStorage.saveDropevent(dropevent);
        sender.sendMessage(messageConfig.getColored("dropevent.success.created"));
        return true;
    }

    @Override
    public boolean isSenderPermitted(CommandSender sender) {
        return sender.hasPermission("cad.dropevents.edit");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}
