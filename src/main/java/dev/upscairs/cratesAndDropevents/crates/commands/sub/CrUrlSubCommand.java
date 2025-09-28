package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class CrUrlSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;


    public CrUrlSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "url";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!isSenderPermitted(sender)) return true;

        if(args.length <= 1) {
            sender.sendMessage(messageConfig.getColored("crate.error.missing-name"));
            return true;
        }
        else if(args.length == 2) {
            sender.sendMessage(messageConfig.getColored("crate.error.missing-url"));
            return true;
        }

        Crate crate = CrateStorage.getCrateById(args[1]);

        if(crate == null) {
            sender.sendMessage(messageConfig.getColored("crate.error.name-not-found"));
            return true;
        }

        crate.setCrateSkullUrl(args[2]);
        CrateStorage.saveCrate(crate);

        sender.sendMessage(messageConfig.getColored("crate.success.skull-updated"));

        return true;
    }

    @Override
    public boolean isSenderPermitted(CommandSender sender) {
        return sender.hasPermission("cad.crates.edit");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(isSenderPermitted(sender) && args.length == 2) return CrateStorage.getCrateIds();

        return Collections.emptyList();
    }
}
