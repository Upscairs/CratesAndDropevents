package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class ItemRewardEvent implements RewardEvent {

    private final ItemStack item;

    public ItemRewardEvent(ItemStack item) {
        this.item = item;
    }

    @Override
    public CompletableFuture<Void> execute(Player player) {
        player.getInventory().addItem(item.clone());
        return CompletableFuture.completedFuture(null);
    }

}
