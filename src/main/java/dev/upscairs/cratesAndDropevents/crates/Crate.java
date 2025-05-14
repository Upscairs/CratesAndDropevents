package dev.upscairs.cratesAndDropevents.crates;

import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Crate {

    private ItemStack crateItem;
    private Map<CrateReward, Integer> rewards;

    public Crate(ItemStack crateItem) {
        this.crateItem = crateItem;
    }

    public Crate(ItemStack crateItem, Map<CrateReward, Integer> rewards) {
        this.crateItem = crateItem;
        this.rewards = rewards;
    }

    public ItemStack getCrateItem() {
        return crateItem;
    }

    public void setCrateItem(ItemStack crateItem) {
        this.crateItem = crateItem;
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



}
