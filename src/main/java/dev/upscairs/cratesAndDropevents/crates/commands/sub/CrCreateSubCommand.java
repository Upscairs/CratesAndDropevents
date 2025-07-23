package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CratesListGui;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrCreateSubCommand implements SubCommand {

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
