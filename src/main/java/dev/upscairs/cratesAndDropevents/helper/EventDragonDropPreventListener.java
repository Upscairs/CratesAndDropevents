package dev.upscairs.cratesAndDropevents.helper;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EventDragonDropPreventListener implements Listener {

    /**
     * Prevents dragon with "NO_XP" tag from dropping XP when killed.
     * @param event
     */
    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof EnderDragon && entity.hasMetadata("NO_XP")) {
            event.setDroppedExp(0);
            event.getDrops().clear();
        }

    }


}
