package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

public class CommandRewardEvent implements CrateRewardEvent {

    private final String command;
    private final Plugin plugin;

    public CommandRewardEvent(String command, Plugin plugin) {
        this.command = command;
        this.plugin = plugin;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public CompletableFuture<Void> execute(Player player, Location location) {
        String resolved = command.replace("{player}", player.getName());
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), resolved));
        return CompletableFuture.completedFuture(null);
    }
}
