package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CrateListGui;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CrListSubCommand implements SubCommand {

    private Plugin plugin;

    public CrListSubCommand(Plugin plugin) {
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

        CrateListGui cratesListGui = new CrateListGui(sender, plugin);
        p.openInventory(cratesListGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
