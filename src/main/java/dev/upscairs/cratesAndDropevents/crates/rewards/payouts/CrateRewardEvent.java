package dev.upscairs.cratesAndDropevents.crates.rewards.payouts;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface CrateRewardEvent {

    CompletableFuture<Void> execute(Player player, Location location);

}
