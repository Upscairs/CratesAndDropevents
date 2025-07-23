package dev.upscairs.cratesAndDropevents.crates.rewards;

import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@SerializableAs("CrateReward")
public class CrateReward implements ConfigurationSerializable {

    private String name;
    private List<CrateRewardEvent> sequence;

    private Plugin plugin;

    public CrateReward(String name, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
        this.sequence = Collections.emptyList();
    }

    public CrateReward(String name, List<CrateRewardEvent> sequence, Plugin plugin) {
        this.name = name;
        this.sequence = Collections.unmodifiableList(sequence);
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

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("name", name);
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

    @SuppressWarnings("unchecked")
    public static CrateReward deserialize(Map<String, Object> map) {

        Plugin plugin = CratesAndDropevents.getInstance();

        String id = (String) map.get("name");
        if (id == null) {
            throw new IllegalArgumentException("Missing name for CrateReward");
        }

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
                    seq.add(new DelayRewardEvent(((Number) m.get("ticks")).longValue(), plugin));
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

        return new CrateReward(id, seq, plugin);
    }


}
