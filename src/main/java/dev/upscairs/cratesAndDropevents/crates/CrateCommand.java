package dev.upscairs.cratesAndDropevents.crates;

import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CrateCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        //TODO
    }

    public void register(SubCommand cmd) {
        subcommands.put(cmd.name(), cmd);
        cmd.aliases().forEach(a -> subcommands.put(a, cmd));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        registerCommands();

        if(args[0].equalsIgnoreCase("test")) {
            Crate crate = new Crate(args[1], plugin);
            if(sender instanceof Player p) {
                p.getInventory().addItem(crate.getCrateItem());
            }

            CrateStorage.saveCrate(crate);

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }



}
