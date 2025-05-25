package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class ItemRewardEvent implements CrateRewardEvent {

    private final ItemStack item;

    public ItemRewardEvent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public CompletableFuture<Void> execute(Player player) {
        player.getInventory().addItem(item.clone());
        return CompletableFuture.completedFuture(null);
    }

}
