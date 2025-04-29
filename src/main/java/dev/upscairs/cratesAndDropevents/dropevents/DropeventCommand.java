package dev.upscairs.cratesAndDropevents.dropevents;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.dropevents.gui_implementations.DropeventEditGui;
import dev.upscairs.cratesAndDropevents.dropevents.gui_implementations.DropeventListGui;
import dev.upscairs.cratesAndDropevents.dropevents.management.ActiveDropEvent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventManager;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventRunner;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DropeventCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;

    public DropeventCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ChatMessageConfig messageConfig = ((CratesAndDropevents) plugin).getChatMessageConfig();

        if(!(sender instanceof Player p)) return true;

        if(args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            UUID id;

            try {
                id = UUID.fromString(args[1]);
            } catch (IllegalArgumentException e) {
                p.sendMessage(messageConfig.getColored("dropevent.error.unknown-id"));
                return true;
            }

            ActiveDropEvent ade = DropEventManager.get(id);
            if (ade == null || !ade.isActive()) {
                p.sendMessage(messageConfig.getColored("dropevent.error.event-over"));
                return true;
            }

            if (!ade.getEvent().isTeleportable()) {
                p.sendMessage(messageConfig.getColored("dropevent.error.not-teleportable"));
                return true;
            }

            if (!ade.canTeleport(p.getUniqueId())) {
                p.sendMessage(messageConfig.getColored("dropevent.error.already-teleported"));
                return true;
            }

            p.teleport(ade.getCenter());
            p.sendMessage(messageConfig.getColored("dropevent.success.teleported"));
            return true;

        }


        if (!p.isOp()) {
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("list")) {
                DropeventListGui dropeventListGui = new DropeventListGui(sender);
                p.openInventory(dropeventListGui.getInventory());
                return true;
            }
            if (args[0].equalsIgnoreCase("info")
                    || args[0].equalsIgnoreCase("start")
                    || args[0].equalsIgnoreCase("create")
                    || args[0].equalsIgnoreCase("remove")) {
                p.sendMessage(messageConfig.getColored("system.command.error.missing-name"));
                return true;
            }
            if(args[0].equalsIgnoreCase("stopAll")) {
                DropEventManager.stopAll();
                p.sendMessage(messageConfig.getColored("dropevent.success.all-stopped"));
                return true;
            }

        }

        if (args.length >= 2) {

            if (args[0].equalsIgnoreCase("info")) {

                Dropevent event = DropeventStorage.getDropeventByName(args[1]);

                if (event == null) {
                    p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
                    return true;
                }

                DropeventEditGui editGui = new DropeventEditGui(event, false, sender, plugin);
                p.openInventory(editGui.getInventory());
                return true;

            } else if (args[0].equalsIgnoreCase("start")) {

                Dropevent event = DropeventStorage.getDropeventByName(args[1]);

                String locationName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (event == null) {
                    p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
                    return true;
                }

                DropEventRunner der = new DropEventRunner(event, p, plugin);
                der.startCountdown(locationName);
                return true;

            } else if (args[0].equalsIgnoreCase("create")) {

                if (args.length > 2) {
                    p.sendMessage(messageConfig.getColored("system.command.error.name-no-spaces"));
                    return true;
                }

                String eventName = args[1];

                if (DropeventStorage.getDropeventByName(eventName) != null) {
                    p.sendMessage(messageConfig.getColored("dropevent.error.name-already-exists"));
                    return true;
                }

                Dropevent dropevent = new Dropevent(eventName);
                DropeventStorage.saveDropevent(dropevent);
                p.sendMessage(messageConfig.getColored("dropevent.success.created"));
                return true;

            } else if (args[0].equalsIgnoreCase("remove")) {

                Dropevent dropevent = DropeventStorage.getDropeventByName(args[1]);

                if (dropevent == null) {
                    p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
                    return true;
                }

                DropeventStorage.removeDropevent(dropevent);
                p.sendMessage(messageConfig.getColored("dropevent.success.removed"));

                return true;
            }


        }

        if (args.length >= 4) {

            if (args[0].equalsIgnoreCase("edit")) {

                String eventName = args[1];
                String setting = args[2];
                String value = args[3];

                Dropevent event = DropeventStorage.getDropeventByName(eventName);

                if (event == null) {
                    p.sendMessage(messageConfig.getColored("dropevent.error.name-not-found"));
                    return true;
                }

                boolean success = event.changeSetting(setting, value);

                DropeventStorage.saveDropevent(event);

                if (success) {
                    p.sendMessage(messageConfig.getColored("dropevent.success.setting-changed"));
                } else {
                    p.sendMessage(messageConfig.getColored("dropevent.error.setting-update-failed"));
                }

                return true;

            }


        }

        p.sendMessage(messageConfig.getColored("system.command.error.not-found"));

        return true;
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
                return Arrays.asList("list", "info", "start", "create", "remove", "edit", "stopAll", "tp");
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
        }

        return null;

    }

}
