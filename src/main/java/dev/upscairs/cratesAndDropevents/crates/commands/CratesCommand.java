package dev.upscairs.cratesAndDropevents.crates.commands;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.resc.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.commands.sub.*;
import dev.upscairs.cratesAndDropevents.resc.CrateStorage;
import dev.upscairs.cratesAndDropevents.helper.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CratesCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CratesCommand(Plugin plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    public void registerCommands() {
        CratesAndDropevents p = (CratesAndDropevents) plugin;

        register(new CrCloneSubCommand(p));
        register(new CrCreateSubCommand(p));
        register(new CrUrlSubCommand(p));
        register(new CrGiveSubCommand(p));
        register(new CrInfoSubCommand(p));
        register(new CrListSubCommand(p));
        register(new CrRewardsSubCommand(p));
        register(new CrCancelSubCommand(p));
        register(new CrDeleteSubCommand(p));
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

        if(!sender.isOp()) return Arrays.asList();

        if(args.length == 1) {
            return Arrays.asList("cancel", "clone", "create", "delete", "give", "info", "list", "rewards", "url");
        }

        List<String> allCrates = CrateStorage.getCrateIds();
        List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

        if(args.length == 2) {

            switch(args[0]) {
                case "clone", "delete", "info", "rewards", "url" -> {
                    return allCrates;
                }
                case "give" -> {
                    return onlinePlayers;
                }
            }
        }

        if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return allCrates;
        }


        return Arrays.asList();
    }



}
