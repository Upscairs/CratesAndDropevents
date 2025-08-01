package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.mcGuiFramework.McGuiFramework;
import dev.upscairs.mcGuiFramework.utility.GuiSounds;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrCreateSubCommand implements SubCommand {

    private ChatMessageConfig messageConfig;
    private CratesAndDropevents plugin;

    public CrCreateSubCommand(CratesAndDropevents plugin) {
        messageConfig = plugin.getChatMessageConfig();
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

        if(!hasPermission(sender)) return true;

        if(args.length <= 1) {
            sender.sendMessage(messageConfig.getColored("crate.error.missing-name"));
            return true;
        }

        if (args.length > 2) {
            if(sender instanceof Player p) McGuiFramework.getGuiSounds().playFailSound(p);
            sender.sendMessage(messageConfig.getColored("dropevent.error.name-no-spaces"));
            return true;
        }

        if(CrateStorage.getCrateById(args[1]) != null) {
            sender.sendMessage(messageConfig.getColored("crate.error.already-exists"));
            if(sender instanceof Player p) McGuiFramework.getGuiSounds().playFailSound(p);
            return true;
        }

        Crate crate = new Crate(args[1], plugin);
        CrateStorage.saveCrate(crate);

        sender.sendMessage(messageConfig.getColored("crate.success.created"));

        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
