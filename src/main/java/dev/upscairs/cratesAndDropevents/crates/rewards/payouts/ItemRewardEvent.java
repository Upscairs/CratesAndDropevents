package dev.upscairs.cratesAndDropevents.crates.rewards.payouts;

import org.bukkit.Location;
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
    public CompletableFuture<Void> execute(Player player, Location location) {

        /*
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(item.clone());

        for (ItemStack left : leftover.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), left);
        }*/

        player.getWorld().dropItemNaturally(location, item.clone());

        return CompletableFuture.completedFuture(null);
    }

}
