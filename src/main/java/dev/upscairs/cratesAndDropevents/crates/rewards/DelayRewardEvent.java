package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

public class DelayRewardEvent implements CrateRewardEvent {

    private final long ticks;
    private final Plugin plugin;

    public DelayRewardEvent(long ticks, Plugin plugin) {
        this.ticks = ticks;
        this.plugin = plugin;
    }

    public long getTicks() {
        return ticks;
    }

    @Override
    public CompletableFuture<Void> execute(Player player, Location location) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLater(plugin, () -> future.complete(null), ticks);
        return future;
    }

}
