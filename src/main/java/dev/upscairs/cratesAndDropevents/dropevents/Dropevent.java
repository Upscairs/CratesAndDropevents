package dev.upscairs.cratesAndDropevents.dropevents;

import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import dev.upscairs.mcGuiFramework.utility.ListableGuiObject;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dropevent implements ListableGuiObject, ConfigurationSerializable {

    private String name;
    private ItemStack renderItem;

    private int dropRange;
    private int eventTimeSec;
    private HashMap<ItemStack, Integer> drops; //Drops with their chances in per mille
    private int dropCount;
    private int countdownSec;
    private boolean broadcast;
    private boolean teleportable;

    public Dropevent(String name) {
        this.name = name;
        renderItem = new ItemStack(Material.FIREWORK_ROCKET, 1);
        dropRange = 100;
        eventTimeSec = 120;
        dropCount = 120;
        drops = new HashMap<>();
        countdownSec = 120;
        broadcast = true;
    }

    public Dropevent(String name, ItemStack representingItem, int dropRange, int eventTimeSec, int dropCount, int countdownSec, boolean broadcast) {
        this.name = name;
        this.renderItem = representingItem;
        this.dropRange = dropRange;
        this.eventTimeSec = eventTimeSec;
        this.drops = new HashMap<>();
        this.dropCount = dropCount;
        this.countdownSec = countdownSec;
        this.broadcast = broadcast;
    }

    public Dropevent(String name, ItemStack renderItem, int dropRange, int eventTimeSec, HashMap<ItemStack, Integer> drops, int dropCount, int countdownSec, boolean broadcast,  boolean teleportable) {
        this.name = name;
        this.renderItem = renderItem;

        this.dropRange  = dropRange;
        this.eventTimeSec  = eventTimeSec;
        this.drops = drops;
        this.dropCount  = dropCount;
        this.countdownSec  = countdownSec;
        this.broadcast = broadcast;
        this.teleportable = teleportable;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * Render item used to list instances of this class in listing guis.
     *
     * @return
     */
    public ItemStack getRenderItem() {
        ItemStack item = renderItem;

        item.setAmount(1);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent(name, "#FFAA00"));

        item.setItemMeta(meta);
        return renderItem;
    }

    public void setRenderItem(ItemStack renderItem) {
        this.renderItem = renderItem;
    }

    public int getDropRange() {
        return dropRange;
    }

    public void setDropRange(int dropRange) {
        this.dropRange = dropRange;
    }

    public int getEventTimeSec() {
        return eventTimeSec;
    }

    public void setEventTimeSec(int eventTimeSec) {
        this.eventTimeSec = eventTimeSec;
    }

    public int getDropCount() {
        return dropCount;
    }

    public void setDropCount(int dropCount) {
        this.dropCount = dropCount;
    }

    public void setItemDropChance(ItemStack item, int chance) {
        drops.put(item, chance);
    }

    public HashMap<ItemStack, Integer> getDrops() {
        return drops;
    }

    public void removeDrop(ItemStack item) {
        drops.remove(item);
    }

    public int getCountdownSec() {
        return countdownSec;
    }

    public void setCountdownSec(int countdownSec) {
        this.countdownSec = countdownSec;
    }

    public boolean isBroadcasting() {
        return broadcast;
    }

    public void setBroadcasting(boolean broadcasting) {
        this.broadcast = broadcasting;
    }

    public boolean isTeleportable() {
        return teleportable;
    }

    public void setTeleportable(boolean teleportable) {
        this.teleportable = teleportable;
    }

    public Dropevent clone() {

        HashMap<ItemStack, Integer> clonedDrops = new HashMap<>();
        for (Map.Entry<ItemStack, Integer> entry : drops.entrySet()) {
            clonedDrops.put(entry.getKey().clone(), entry.getValue());
        }

        return new Dropevent(name, renderItem.clone(), dropRange, eventTimeSec, clonedDrops, dropCount, countdownSec, broadcast, teleportable);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Adjust a setting via key-value-pair. Useful for command inputs
     *
     * @param setting
     * @param value
     * @return
     */
    public boolean changeSetting(String setting, String value) {

        if(setting.equalsIgnoreCase("renderItem")) {
            renderItem = new ItemStack(Material.getMaterial(value.toUpperCase()));
            return true;
        }

        int newValue;

        try {
            newValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }

        if(newValue < 0 || newValue > 20000) return false;

        switch (setting.toLowerCase()) {
            case "range": dropRange = newValue; return true;
            case "duration": eventTimeSec = newValue; return true;
            case "drops": dropCount = newValue; return true;
            case "countdown": countdownSec = newValue; return true;
            default: return false;
        }

    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("renderItem", renderItem);
        map.put("dropRange", dropRange);
        map.put("eventTimeSec", eventTimeSec);
        map.put("dropCount", dropCount);
        map.put("countdownSec", countdownSec);
        map.put("broadcast", broadcast);
        map.put("teleportable", teleportable);

        //Drops list
        List<Map<String, Object>> dropsList = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : drops.entrySet()) {
            Map<String, Object> dropMap = new HashMap<>();
            dropMap.put("item", entry.getKey());
            dropMap.put("chance", entry.getValue());
            dropsList.add(dropMap);
        }
        map.put("drops", dropsList);

        return map;
    }

    public static Dropevent deserialize(Map<String, Object> map) {
        Dropevent event = new Dropevent((String) map.get("name"));
        event.setRenderItem((ItemStack) map.get("renderItem"));
        event.setDropRange((int) map.get("dropRange"));
        event.setEventTimeSec((int) map.get("eventTimeSec"));
        event.setDropCount((int) map.get("dropCount"));
        event.setCountdownSec((int) map.get("countdownSec"));

        if (map.containsKey("broadcast")) {
            event.setBroadcasting((boolean) map.get("broadcast"));
        }
        if (map.containsKey("teleportable")) {
            event.setTeleportable((boolean) map.get("teleportable"));
        }

        Object dropsObj = map.get("drops");
        HashMap<ItemStack, Integer> dropsMap = new HashMap<>();

        if (dropsObj instanceof List<?>) {
            List<?> dropsList = (List<?>) dropsObj;
            for (Object dropEntry : dropsList) {
                if (dropEntry instanceof Map<?, ?> dropMap) {
                    ItemStack item = (ItemStack) dropMap.get("item");
                    Number chanceNum = (Number) dropMap.get("chance");
                    dropsMap.put(item, chanceNum.intValue());
                }
            }
        }

        event.drops = dropsMap;
        return event;
    }

}
