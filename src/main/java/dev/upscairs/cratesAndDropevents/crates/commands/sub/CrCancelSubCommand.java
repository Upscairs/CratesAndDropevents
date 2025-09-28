package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.helper.ChatMessageInputHandler;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrCancelSubCommand implements SubCommand {

    private ChatMessageConfig messageConfig;

    public CrCancelSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "cancel";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(!isSenderPermitted(sender)) return true;
        ChatMessageInputHandler.removeListener(sender);
        sender.sendMessage(messageConfig.getColored("crate.info.type-canceled"));
        return true;
    }

    @Override
    public boolean isSenderPermitted(CommandSender sender) {
        return sender.hasPermission("cad.crates.edit");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}
