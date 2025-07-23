package dev.upscairs.cratesAndDropevents.crates.rewards;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class MessageRewardEvent implements CrateRewardEvent {

    private Component message;

    public MessageRewardEvent(Component message) {
        this.message = message;
    }

    public Component getMessage() {
        return message;
    }

    @Override
    public CompletableFuture<Void> execute(Player player, Location location) {

        player.sendMessage(message);

        return CompletableFuture.completedFuture(null);
    }


}
