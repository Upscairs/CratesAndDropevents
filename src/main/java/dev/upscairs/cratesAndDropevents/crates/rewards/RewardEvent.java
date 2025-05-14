package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface RewardEvent {

    CompletableFuture<Void> execute(Player player);

}
