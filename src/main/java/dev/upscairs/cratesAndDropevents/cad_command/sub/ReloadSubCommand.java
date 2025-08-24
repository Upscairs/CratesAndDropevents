package dev.upscairs.cratesAndDropevents.cad_command.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadSubCommand implements SubCommand {

    CratesAndDropevents plugin;

    public ReloadSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return true;
        sender.sendMessage("Reloading configs.");
        plugin.reloadConfigs();
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
