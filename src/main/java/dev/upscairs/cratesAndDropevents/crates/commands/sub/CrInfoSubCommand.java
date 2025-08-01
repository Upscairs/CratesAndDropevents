package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CrateEditGui;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrInfoSubCommand implements SubCommand {

    private final CratesAndDropevents plugin;
    private final ChatMessageConfig messageConfig;

    public CrInfoSubCommand(CratesAndDropevents plugin) {
        this.plugin = plugin;
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "info";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) return true;
        if(!(sender instanceof Player p)) return true;

        if(args.length <= 1) {
            p.sendMessage(messageConfig.getColored("dropevent.error.missing-name"));
            return true;
        }

        Crate crate = CrateStorage.getCrateById(args[1]);

        if (crate == null) {
            p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
            return true;
        }

        CrateEditGui editGui = new CrateEditGui(crate, false, sender, plugin);
        p.openInventory(editGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
