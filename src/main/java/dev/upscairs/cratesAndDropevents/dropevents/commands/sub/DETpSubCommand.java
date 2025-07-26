package dev.upscairs.cratesAndDropevents.dropevents.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.ActiveDropEvent;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropEventManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DETpSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;

    public DETpSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }

    @Override
    public String name() {
        return "tp";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) return true;

        if(!(sender instanceof Player p)) return true;

        UUID id;

        if(args.length == 1) {
            p.sendMessage(messageConfig.getColored("dropevent.error.missing-id"));
            return true;
        }

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

    @Override
    public boolean hasPermission(CommandSender sender) {
        if(!(sender instanceof Player p)) return false;

        return true;
    }
}
