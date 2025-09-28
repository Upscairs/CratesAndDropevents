package dev.upscairs.cratesAndDropevents.cad_command;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.cad_command.sub.ReloadSubCommand;
import dev.upscairs.cratesAndDropevents.cad_command.sub.UpgradeSubCommand;
import dev.upscairs.cratesAndDropevents.cad_command.sub.VersionSubCommand;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaDCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CaDCommand(Plugin plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    public void registerCommands() {

        CratesAndDropevents p = (CratesAndDropevents)  plugin;

        register(new ReloadSubCommand(p));
        register(new VersionSubCommand(p));
        register(new UpgradeSubCommand(p));

    }

    public void register(SubCommand cmd) {
        subcommands.put(cmd.name(), cmd);
        cmd.aliases().forEach(a -> subcommands.put(a, cmd));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            ChatMessageConfig messageConfig = ((CratesAndDropevents) plugin).getChatMessageConfig();
            sender.sendMessage(messageConfig.getColored("system.command.error.not-found"));
            return true;
        }

        ChatMessageConfig messageConfig = ((CratesAndDropevents) plugin).getChatMessageConfig();

        SubCommand handler = subcommands.get(args[0]);
        if(handler == null) {
            sender.sendMessage(messageConfig.getColored("system.command.error.not-found"));
            return true;
        }

        return handler.execute(sender, args);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("cad.admin")) return Arrays.asList();

        if(args.length == 1) {
            return Arrays.asList("reload", "version", "upgrade");
        }

        return Arrays.asList();
    }



}
