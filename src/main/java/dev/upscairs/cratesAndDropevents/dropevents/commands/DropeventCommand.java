package dev.upscairs.cratesAndDropevents.dropevents.commands;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.commands.sub.*;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventManager;
import dev.upscairs.cratesAndDropevents.resc.DropeventStorage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class DropeventCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public DropeventCommand(Plugin plugin) {
        this.plugin = plugin;
        registerCommands();
    }


    public void registerCommands() {

        CratesAndDropevents c = (CratesAndDropevents) plugin;

        register(new DECreateSubCommand(c));
        register(new DEEditSubCommand(c));
        register(new DEGiveSubCommand(c));
        register(new DEInfoSubCommand(c));
        register(new DEListSubCommand());
        register(new DERemoveSubCommand(c));
        register(new DEStartSubCommand(c));
        register(new DEStopAllSubCommand(c));
        register(new DETpSubCommand(c));
    }

    private void register(SubCommand cmd) {
        subcommands.put(cmd.name(), cmd);
        cmd.aliases().forEach(a -> subcommands.put(a, cmd));
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //TODO necessary?
        registerCommands();

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
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(sender instanceof Player p) {

            if(!p.isOp() && args.length == 1) {
                return Arrays.asList("tp");
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
                return DropEventManager.getActive().keySet().stream()
                        .filter(id -> DropEventManager.getActive().get(id).getEvent().isTeleportable())
                        .map(UUID::toString)
                        .collect(Collectors.toList());
            }

            if(!p.isOp()) {
                return null;
            }

            List<String> eventNames = DropeventStorage.getDropeventNames();

            if(args.length == 1) {
                return Arrays.asList("list", "info", "start", "create", "remove", "give", "edit", "stopAll", "tp");
            }
            else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("info")
                || args[0].equalsIgnoreCase("start")
                || args[0].equalsIgnoreCase("remove")) {
                    return eventNames;
                }
            }

            if(args[0].equalsIgnoreCase("edit")) {
                if(args.length == 2) {
                    return eventNames;
                }
                if(args.length == 3) {
                    return Arrays.asList("renderItem", "range", "duration", "drops", "countdown");
                }
                if(args.length == 4 && args[2].equalsIgnoreCase("renderItem")) {
                    String partial = args[3].toUpperCase();
                    return Arrays.stream(Material.values())
                            .map(Material::name)
                            .filter(name -> name.startsWith(partial))
                            .sorted()
                            .collect(Collectors.toList());
                }
            }

            if(args[0].equalsIgnoreCase("give")) {
                if(args.length == 2) {
                    return plugin.getServer().getOnlinePlayers().stream()
                            .map(Player::getName)
                            .collect(Collectors.toList());
                }
                else if(args.length == 3) {
                    return eventNames;
                }
            }
        }

        return null;

    }

}
