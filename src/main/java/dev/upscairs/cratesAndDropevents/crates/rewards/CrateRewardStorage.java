package dev.upscairs.cratesAndDropevents.crates.rewards;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrateRewardStorage {

    private static FileConfiguration config;
    private static File file;
    private static final String fileName = "crate-rewards.yml";

    public static void init(JavaPlugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException ignored) {}
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveReward(CrateReward reward) {
        config.set("rewards." + reward.getName(), reward);
        saveFile();
    }

    public static void removeReward(String id) {
        config.set("rewards." + id, null);
        saveFile();
    }

    public static List<String> getRewardIds() {
        ConfigurationSection section = config.getConfigurationSection("rewards");
        return section != null ? new ArrayList<>(section.getKeys(false)) : new ArrayList<>();
    }

    public static List<CrateReward> getAll() {
        List<CrateReward> list = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("rewards");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Object obj = config.get("rewards." + key);
                if (obj instanceof CrateReward) {
                    list.add((CrateReward) obj);
                }
            }
        }
        return list;
    }

    public static CrateReward getRewardById(String id) {
        if (config.contains("rewards." + id)) {
            Object obj = config.get("rewards." + id);
            if (obj instanceof CrateReward) {
                return (CrateReward) obj;
            }
        }
        return null;
    }

    private static void saveFile() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }
}
