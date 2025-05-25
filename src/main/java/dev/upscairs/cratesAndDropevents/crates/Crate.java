package dev.upscairs.cratesAndDropevents.crates;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.upscairs.cratesAndDropevents.CratesAndDropevents;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateRewardStorage;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@SerializableAs("Crate")
public class Crate implements ConfigurationSerializable {

    private String name;
    private ItemStack crateItem;

    private Map<CrateReward, Integer> rewards = new HashMap<>();

    private final Plugin plugin;

    public Crate(String name, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;

        crateItem = new ItemStack(InvGuiUtils.generateCustomUrlHeadStack("http://textures.minecraft.net/texture/f1327353e2f6364b437f1e6c4a7e9764ea95e27deec0031eec1142df2f949b3"));
        crateItem.setAmount(1);
        ItemMeta meta = crateItem.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent(name, "#FFAA00"));
        crateItem.setItemMeta(meta);

        addCrateFlag();


    }

    public Crate(ItemStack crateItem, Plugin plugin) {
        this.crateItem = crateItem;
        this.name = crateItem.getItemMeta().getDisplayName();
        addCrateFlag();

        this.plugin = plugin;
    }

    public Crate(ItemStack crateItem, Map<CrateReward, Integer> rewards, Plugin plugin) {
        this.crateItem = crateItem;
        this.name = crateItem.getItemMeta().getDisplayName();
        this.rewards = rewards;
        addCrateFlag();

        this.plugin = plugin;
    }

    private void addCrateFlag() {
        ItemMeta meta = crateItem.getItemMeta();
        meta.getPersistentDataContainer().set(
                ((CratesAndDropevents) plugin).CRATE_KEY,
                PersistentDataType.STRING,
                name);
        crateItem.setItemMeta(meta);
    }

    public ItemStack getCrateItem() {
        return crateItem;
    }

    public void setCrateItem(ItemStack crateItem) {
        this.crateItem = crateItem;
        addCrateFlag();
    }

    public boolean setCrateSkullUrl(String url) {

        SkullMeta meta = (SkullMeta)crateItem.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(new URL(url));
        } catch (MalformedURLException var6) {
            Bukkit.getLogger().warning("Head Database seems to be down");
            return false;
        }

        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        crateItem.setItemMeta(meta);
        return true;
    }

    public Map<CrateReward, Integer> getRewards() {
        return rewards;
    }

    public void setRewards(Map<CrateReward, Integer> rewards) {
        this.rewards = rewards;
    }

    public void addReward(CrateReward reward, int chance) {
        rewards.put(reward, chance);
    }

    public void removeReward(CrateReward reward) {
        rewards.remove(reward);
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("crateItem", crateItem);

        List<Map<String, Object>> rewardsList = new ArrayList<>();
        for (Map.Entry<CrateReward, Integer> entry : rewards.entrySet()) {
            Map<String, Object> rewardEntry = new LinkedHashMap<>();
            rewardEntry.put("reward", entry.getKey().getName());
            rewardEntry.put("chance", entry.getValue());
            rewardsList.add(rewardEntry);
        }
        map.put("rewards", rewardsList);

        return map;
    }

    public static Crate deserialize(Map<String, Object> map, Plugin plugin) {

        ItemStack crateItem = (ItemStack) map.get("crateItem");
        Crate crate = new Crate(crateItem, plugin);

        Object obj = map.get("rewards");
        if (obj instanceof List<?> list) {
            for (Object element : list) {
                if (!(element instanceof Map<?, ?> rewardMap)) continue;

                CrateReward reward = CrateRewardStorage.getRewardById(rewardMap.get("reward").toString());
                Number chanceNum = (Number) rewardMap.get("chance");
                crate.addReward(reward, chanceNum.intValue());
            }
        }

        return crate;
    }




}
