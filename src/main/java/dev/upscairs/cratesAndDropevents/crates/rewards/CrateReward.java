package dev.upscairs.cratesAndDropevents.crates.rewards;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.crates.rewards.payouts.*;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import dev.upscairs.mcGuiFramework.utility.ListableGuiObject;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@SerializableAs("CrateReward")
public class CrateReward implements ConfigurationSerializable, ListableGuiObject {

    private List<CrateRewardEvent> sequence;

    private Plugin plugin;

    public CrateReward(Plugin plugin) {
        this.plugin = plugin;
        this.sequence = new ArrayList<>();
    }

    public CrateReward(List<CrateRewardEvent> sequence, Plugin plugin) {
        this.sequence = new ArrayList<>(sequence);
        this.plugin = plugin;
    }


    public CompletableFuture<Void> execute(Player player, Location location) {

        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);

        for (CrateRewardEvent element : sequence) {
            chain = chain.thenCompose(v -> element.execute(player, location));
        }

        return chain;
    }

    public List<CrateRewardEvent> getSequence() {
        return sequence;
    }

    public void addEvent(CrateRewardEvent event) {
        sequence.add(event);
    }

    public CrateReward clone() {
        return new CrateReward(sequence, plugin);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> out = new LinkedHashMap<>();
        List<Map<String, Object>> events = new ArrayList<>();

        for (CrateRewardEvent evt : sequence) {
            Map<String, Object> m = new LinkedHashMap<>();
            if (evt instanceof CommandRewardEvent cre) {
                m.put("type", "command");
                m.put("command", cre.getCommand());
            } else if (evt instanceof MessageRewardEvent mre) {
                m.put("type", "message");
                m.put("message", LegacyComponentSerializer.legacySection().serialize(mre.getMessage()));
            }
            else if (evt instanceof DelayRewardEvent dre) {
                m.put("type", "delay");
                m.put("ticks", dre.getTicks());
            } else if (evt instanceof ItemRewardEvent ire) {
                m.put("type", "item");
                m.put("item", ire.getItem());
            } else if (evt instanceof SoundRewardEvent sre) {
                m.put("type", "sound");
                m.put("soundName", sre.getSoundName());
                m.put("volume", sre.getVolume());
                m.put("pitch", sre.getPitch());
            }
            events.add(m);
        }
        out.put("events", events);
        return out;
    }

    public static CrateReward deserialize(Map<String, Object> map) {

        Plugin plugin = CratesAndDropevents.getInstance();

        Object eventsObj = map.get("events");
        if (!(eventsObj instanceof List<?> rawList)) {
            throw new IllegalArgumentException("Missing or invalid 'events' for CrateReward");
        }

        List<CrateRewardEvent> seq = new ArrayList<>();
        for (Object o : rawList) {
            if (!(o instanceof Map<?, ?> m)) continue;
            String type = (String) m.get("type");
            switch (type) {
                case "command":
                    seq.add(new CommandRewardEvent((String) m.get("command"), plugin));
                    break;
                case "message":
                    seq.add(new MessageRewardEvent(LegacyComponentSerializer.legacySection().deserialize((String) m.get("message"))));
                    break;
                case "delay":
                    seq.add(new DelayRewardEvent(((Number) m.get("ticks")).intValue(), plugin));
                    break;
                case "item":
                    seq.add(new ItemRewardEvent((ItemStack) m.get("item")));
                    break;
                case "sound":
                    seq.add(new SoundRewardEvent(
                            (String) m.get("soundName"),
                            ((Number) m.get("volume")).floatValue(),
                            ((Number) m.get("pitch")).floatValue()
                    ));
                    break;
            }
        }

        return new CrateReward(seq, plugin);
    }


    @Override
    public ItemStack getRenderItem() {

        ItemRewardEvent irw = null;
        CommandRewardEvent crw = null;
        MessageRewardEvent mrw = null;
        SoundRewardEvent sre = null;

        int moreEvents = sequence.size() - 1;

        for(CrateRewardEvent evt : sequence.reversed()) {
            if(evt instanceof ItemRewardEvent) {
                irw = (ItemRewardEvent) evt;
            }
            else if(evt instanceof CommandRewardEvent) {
                crw = (CommandRewardEvent) evt;
            }
            else if(evt instanceof MessageRewardEvent) {
                mrw = (MessageRewardEvent) evt;
            }
            else if(evt instanceof SoundRewardEvent) {
                sre = (SoundRewardEvent) evt;
            }

        }

        ItemStack item = new ItemStack(Material.SCAFFOLDING);
        String name = "";

        if(irw != null) {
            item = irw.getItem().clone();
            name = "Drop " + irw.getItem().getI18NDisplayName();
        }
        else if(crw != null) {
            item = new ItemStack(Material.COMMAND_BLOCK);
            name = "Run command";
        }
        else if(mrw != null) {
            item = new ItemStack(Material.PAPER);
            name = "Send message";
        }
        else if(sre != null) {
            item = new ItemStack(Material.NOTE_BLOCK);
            name = "Play sound";
        }


        if(name.isEmpty()) {
            name = "Empty";
        }
        else {
            if(moreEvents > 0) name += " + " + moreEvents + " more";
        }

        ItemMeta meta = item.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultHeaderComponent(name, "#FFAA00"));

        item.setItemMeta(meta);
        return item;

    }
}
