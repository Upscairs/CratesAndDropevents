package dev.upscairs.cratesAndDropevents.crates.commands;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.commands.sub.*;
import dev.upscairs.cratesAndDropevents.crates.management.Crate;
import dev.upscairs.cratesAndDropevents.crates.rewards.*;
import dev.upscairs.cratesAndDropevents.crates.rewards.payouts.*;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import dev.upscairs.cratesAndDropevents.dropevents.commands.sub.*;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CratesCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CratesCommand(Plugin plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    public void registerCommands() {
        CratesAndDropevents c = (CratesAndDropevents) plugin;

        register(new CrCreateSubCommand());
        register(new CrEditSubCommand(c));
        register(new CrGiveSubCommand());
        register(new CrInfoSubCommand());
        register(new CrListSubCommand());
    }

    public void register(SubCommand cmd) {
        subcommands.put(cmd.name(), cmd);
        cmd.aliases().forEach(a -> subcommands.put(a, cmd));
    }

    @Override
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

        /*
        if(args[0].equalsIgnoreCase("test")) {
            Crate crate = new Crate(args[1], plugin);
            if(sender instanceof Player p) {
                p.getInventory().addItem(crate.getCrateItem());
            }

            List<CrateRewardEvent> rewards = new ArrayList<>();

            rewards.add(new MessageRewardEvent(InvGuiUtils.generateDefaultTextComponent("Dropping an item and waiting 2 secs..", "#FFFFFF")));
            rewards.add(new ItemRewardEvent(new ItemStack(Material.DIAMOND)));
            rewards.add(new DelayRewardEvent(40, plugin));
            rewards.add(new MessageRewardEvent(InvGuiUtils.generateDefaultTextComponent("Now playing a sound. Waiting 4 secs for drama", "#FFFFFF")));
            rewards.add(new SoundRewardEvent("minecraft:item.totem.use", 1, 2));
            rewards.add(new DelayRewardEvent(80, plugin));
            rewards.add(new CommandRewardEvent("say Now executing /say command as console.", plugin));
            rewards.add(new DelayRewardEvent(80, plugin));
            rewards.add(new MessageRewardEvent(InvGuiUtils.generateDefaultTextComponent("And finally dropping an item again", "#FFFFFF")));
            rewards.add(new ItemRewardEvent(new ItemStack(Material.ACACIA_BOAT)));


            CrateReward crateReward = new CrateReward(args[1], rewards, plugin);


            ((CratesAndDropevents) plugin).getCrateRewardStorage().saveReward(crateReward);

            crate.addReward(crateReward, 1000);

            System.out.println(crate.getName());

            ((CratesAndDropevents) plugin).getCrateStorage().saveCrate(crate);

        }

        return true;*/

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }



}
