package dev.upscairs.cratesAndDropevents.crates.commands.sub;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrGiveSubCommand implements SubCommand {

    private final ChatMessageConfig messageConfig;

    public CrGiveSubCommand(CratesAndDropevents plugin) {
        this.messageConfig = plugin.getChatMessageConfig();
    }


    @Override
    public String name() {
        return "give";
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return true;
        if (!(sender instanceof Player p)) return true;

        if (args.length <= 1) {
            sender.sendMessage(messageConfig.getColored("crate.error.missing-player"));
            return true;
        }

        if(args.length == 2) {
            sender.sendMessage(messageConfig.getColored("crate.error.missing-name"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if(target == null) {
            sender.sendMessage(messageConfig.getColored("crate.error.player-not-found"));
            return true;
        }

        Crate crate = CrateStorage.getCrateById(args[2]);

        if(crate == null) {
            sender.sendMessage(messageConfig.getColored("crate.error.name-not-found"));
            return true;
        }


        int amount = 1;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException ignored) {}

        ItemStack item = crate.getCrateItem().clone();
        item.setAmount(amount);

        target.getInventory().addItem(item);

        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.isOp();
    }
}
