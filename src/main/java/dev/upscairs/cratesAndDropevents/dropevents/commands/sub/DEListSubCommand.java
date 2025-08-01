package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.gui_implementations.DropeventListGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class DEListSubCommand implements SubCommand {

    private Plugin plugin;

    public DEListSubCommand(Plugin plugin) {
        this.plugin = plugin;
    }


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

        DropeventListGui dropeventListGui = new DropeventListGui(sender, plugin);
        p.openInventory(dropeventListGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
