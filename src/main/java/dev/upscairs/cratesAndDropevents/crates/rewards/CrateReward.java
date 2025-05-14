package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CrateReward {

    private List<RewardEvent> sequence;

    public CrateReward(List<RewardEvent> sequence) {
        this.sequence = Collections.unmodifiableList(sequence);
    }


    public CompletableFuture<Void> execute(Player player) {

        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);

        for (RewardEvent element : sequence) {
            chain = chain.thenCompose(v -> element.execute(player));
        }

        return chain;
    }


}
