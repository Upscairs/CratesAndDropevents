package dev.upscairs.cratesAndDropevents.crates.rewards.payouts;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SoundRewardEvent implements CrateRewardEvent {
    private final String soundName;
    private final float volume;
    private final float pitch;

    public SoundRewardEvent(String soundName, float volume, float pitch) {
        this.soundName = soundName;
        this.volume = volume;
        this.pitch = pitch;
    }

    public String getSoundName() {
        return soundName;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public CompletableFuture<Void> execute(Player player, Location location) {
        player.playSound(player.getLocation(), soundName, volume, pitch);
        return CompletableFuture.completedFuture(null);
    }


}
