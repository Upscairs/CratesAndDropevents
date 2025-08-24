package dev.upscairs.cratesAndDropevents.helper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class BossbarCountdown {

    private final Plugin plugin;
    private final Location center;
    private final double radius;
    private final long durationMillis;
    private final BossBar bossBar;

    private BukkitTask task;
    private final Set<UUID> shownPlayers = new HashSet<>();
    private long startTime;

    private static final HashMap<BossbarCountdown, Location> activeBossbars = new HashMap<>();

    public BossbarCountdown(Plugin plugin, Location center, double radius, long durationSeconds) {
        this.plugin = plugin;
        this.center = center.clone();
        this.radius = radius;
        this.durationMillis = durationSeconds * 1000L;

        this.bossBar = Bukkit.createBossBar("Starting...", BarColor.GREEN, BarStyle.SOLID);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);
    }

    public void start() {

        if(bossbarInRangeExists(center, radius)) {
            return;
        }
        activeBossbars.put(this, center.clone());


        this.startTime = System.currentTimeMillis();

        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long now = System.currentTimeMillis();
            long elapsed = now - startTime;
            double remainingFraction = 1.0 - (double) elapsed / (double) durationMillis;
            if (remainingFraction < 0) remainingFraction = 0;

            bossBar.setProgress(Math.max(0.0, Math.min(1.0, remainingFraction)));

            ChatColor chatColor;

            if (remainingFraction > 0.66) {
                bossBar.setColor(BarColor.GREEN);
                chatColor = ChatColor.GREEN;
            } else if (remainingFraction > 0.33) {
                bossBar.setColor(BarColor.YELLOW);
                chatColor = ChatColor.YELLOW;
            } else {
                bossBar.setColor(BarColor.RED);
                chatColor = ChatColor.RED;
            }

            long secondsLeft = Math.max(0L, (long) Math.ceil(remainingFraction * durationMillis / 1000.0));
            bossBar.setTitle(ChatColor.BOLD + "" + chatColor + formatTime(secondsLeft));

            //Manage players in radius
            if (center.getWorld() != null) {
                for (Player p : center.getWorld().getPlayers()) {
                    double dx = p.getLocation().getX() - center.getX();
                    double dz = p.getLocation().getZ() - center.getZ();
                    double dy = p.getLocation().getY() - center.getY();
                    double distSq = dx*dx + dy*dy + dz*dz;
                    if (distSq <= radius * radius) {
                        // Spieler ist im Radius
                        if (shownPlayers.add(p.getUniqueId())) {
                            bossBar.addPlayer(p);
                        }
                    } else {
                        // Spieler ist außerhalb
                        if (shownPlayers.remove(p.getUniqueId())) {
                            bossBar.removePlayer(p);
                        }
                    }
                }
            }

            if (elapsed >= durationMillis) {
                stop();
            }
        }, 0L, 1L);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        for (Player p : new ArrayList<>(bossBar.getPlayers())) {
            bossBar.removePlayer(p);
        }
        shownPlayers.clear();
        bossBar.setVisible(false);
        activeBossbars.remove(this);
    }

    private String formatTime(long seconds) {
        long m = seconds / 60;
        long s = seconds % 60;
        return String.format("%d:%02d", m, s);
    }

    private boolean bossbarInRangeExists(Location center, double radius) {
        for(BossbarCountdown bossbar : activeBossbars.keySet()) {
            double centerDistances = center.distance(bossbar.center);
            if (centerDistances <= radius) {
                return true;
            }
        }
        return false;
    }
}