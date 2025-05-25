package dev.upscairs.cratesAndDropevents.crates.rewards;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.dropevents.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateRewardCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CrateRewardCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {

    }

    public void register(SubCommand cmd) {
        subcommands.put(cmd.name(), cmd);
        cmd.aliases().forEach(a -> subcommands.put(a, cmd));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        registerCommands();

        if (args[0].equalsIgnoreCase("test")) {

            List<CrateRewardEvent> rewards = new ArrayList<>();

            rewards.add(new CommandRewardEvent("/test", plugin));
            rewards.add(new DelayRewardEvent(1000, plugin));
            rewards.add(new ItemRewardEvent(new ItemStack(Material.ACACIA_BOAT)));
            rewards.add(new SoundRewardEvent("hallo", 1, 1));

            CrateReward crateReward = new CrateReward(args[1], rewards, plugin);


            ((CratesAndDropevents) plugin).getCrateRewardStorage().saveReward(crateReward);
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }


}
