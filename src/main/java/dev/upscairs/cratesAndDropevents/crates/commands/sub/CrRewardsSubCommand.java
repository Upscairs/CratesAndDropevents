package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.gui_implementations.CrateRewardsGui;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CrRewardsSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;
    private final Plugin plugin;

    public CrRewardsSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "rewards";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) return true;
        if(!(sender instanceof Player p))  return true;

        if(args.length <= 1) {
            sender.sendMessage(messageConfig.getColored("system.command.error.not-enough-arguments"));
        }

        Crate crate = CrateStorage.getCrateById(args[1]);

        if(crate == null) {
            sender.sendMessage(messageConfig.getColored("crate.error.name-not-found"));
            return true;
        }

        CrateRewardsGui cratesDropsGui = new CrateRewardsGui(crate, sender, plugin);
        p.openInventory(cratesDropsGui.getGui().getInventory());
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
