package dev.upscairs.cratesAndDropevents.dropevents.management;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.helper.BossbarCountdown;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveDropEvent {
    private final UUID id;
    private final Dropevent event;
    private final Location center;
    private final long startMs;
    private final Set<UUID> teleported = ConcurrentHashMap.newKeySet();

    private BossbarCountdown  bossbarCountdown;

    private final List<BukkitTask> tasks = new ArrayList<>();

    public ActiveDropEvent(Dropevent event, Location center) {
        this.id = UUID.randomUUID();
        this.event = event;
        this.center = center.clone();
        this.startMs = System.currentTimeMillis() + event.getCountdownSec() * 1000L;
    }

    public UUID getId() { return id; }
    public Location getCenter() { return center; }

    public boolean isActive() {
        long now = System.currentTimeMillis();
        return now <= (startMs + event.getEventTimeSec() * 1000L + event.getCountdownSec() * 1000L);
    }

    public boolean canTeleport(UUID player) {
        return isActive() && teleported.add(player);
    }

    public Dropevent getEvent() {
        return event;
    }

    public void addTask(BukkitTask task) {
        tasks.add(task);
    }
    public void stop() {
        for (BukkitTask t : tasks) t.cancel();
        tasks.clear();
    }

    public void setBossbarCountdown(BossbarCountdown bossbarCountdown) {
        this.bossbarCountdown = bossbarCountdown;
    }

    public BossbarCountdown getBossbarCountdown() {
        return bossbarCountdown;
    }

}

