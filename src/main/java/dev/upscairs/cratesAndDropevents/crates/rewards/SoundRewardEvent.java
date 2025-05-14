package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SoundRewardEvent implements RewardEvent {
    private final String soundName;
    private final float volume;
    private final float pitch;

    public SoundRewardEvent(String soundName, float volume, float pitch) {
        this.soundName = soundName;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public CompletableFuture<Void> execute(Player player) {
        player.playSound(player.getLocation(), soundName, volume, pitch);
        return CompletableFuture.completedFuture(null);
    }
}
