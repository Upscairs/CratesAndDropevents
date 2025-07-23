package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrGiveSubCommand implements SubCommand {
    @Override
    public String name() {
        return "";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return false;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return false;
    }
}
