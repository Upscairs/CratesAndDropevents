package dev.upscairs.cratesAndDropevents.dropevents.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class DeTpSubCommand implements SubCommand{
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
    public boolean checkPermission(CommandSender sender) {
        return false;
    }
}
