package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CratesListGui;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrListSubCommand implements SubCommand {

    @Override
    public String name() {
        return "list";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!hasPermission(sender)) return true;
        if(!(sender instanceof Player p)) return true;

        CratesListGui cratesListGui = new CratesListGui(sender);
        p.openInventory(cratesListGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
