package dev.upscairs.cratesAndDropevents.crates;

import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CrateOpener {

    public void openCrate(Crate crate, Player player, Location location) {

        List<Map.Entry<CrateReward, Integer>> rewards = new ArrayList<>(crate.getRewards().entrySet());

        int pickedNumber = new Random().nextInt(1000);

        for(Map.Entry<CrateReward, Integer> reward : rewards) {

            int weight = reward.getValue();

            if (pickedNumber >= weight) {
                pickedNumber -= weight;
            } else {
                reward.getKey().execute(player, location);
                return;
            }

        }
    }

}
