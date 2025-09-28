package dev.upscairs.cratesAndDropevents.helper;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface SubCommand extends TabCompleter {
    String name();
    List<String> aliases();
    boolean execute(CommandSender sender, String[] args);
    boolean isSenderPermitted(CommandSender sender);
}
