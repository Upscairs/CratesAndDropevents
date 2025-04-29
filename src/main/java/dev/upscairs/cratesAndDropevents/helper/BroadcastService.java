package dev.upscairs.cratesAndDropevents.helper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class BroadcastService {

    /**
     *
     * Sends a global broadcast message to all players on the server.
     *
     * @param message
     */
    public static void sendGlobalBroadcast(String message) {
        Bukkit.broadcast(Component.text(message));
    }

    public static void sendGlobalBroadcast(Component message) {
        Bukkit.broadcast(message);
    }

    /**
     *
     * Sends a global broadcast message to all players on the server with a custom color.
     *
     * @param message
     * @param color Color as hex string
     */
    public static void sendGlobalBroadcast(String message, String color) {

        Component messageComponent = Component.text(message).color(TextColor.fromHexString(color));
        Bukkit.broadcast(messageComponent);

    }

    /**
     *
     * Sends a local broadcast message to all players within a given radius of a given location with a custom Color. The target area is a square.
     *
     * @param center
     * @param radius
     * @param message
     * @param color Color as hex string
     */
    public static void sendLocalBroadcast(Location center, double radius, String message, String color) {
        Component messageComponent = Component.text(message).color(TextColor.fromHexString(color));
        sendLocalBroadcast(center, radius, messageComponent);
    }

    /**
     *
     * Sends a local broadcast message to all players within a given radius of a given location. The target area is a square.
     *
     * @param center
     * @param radius
     * @param message
     */
    public static void sendLocalBroadcast(Location center, double radius, String message) {
        Component messageComponent = Component.text(message);
        sendLocalBroadcast(center, radius, messageComponent);

    }

    public static void sendLocalBroadcast(Location center, double radius, Component message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(center.getWorld())) continue;

            Location playerLoc = player.getLocation();

            double dx = Math.abs(playerLoc.getX() - center.getX());
            double dz = Math.abs(playerLoc.getZ() - center.getZ());

            if (dx <= radius && dz <= radius) {
                player.sendMessage(message);
            }
        }
    }





}
