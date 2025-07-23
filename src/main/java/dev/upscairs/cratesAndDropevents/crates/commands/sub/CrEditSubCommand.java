package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrEditSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;


    public CrEditSubCommand(CratesAndDropevents plugin) {
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
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
