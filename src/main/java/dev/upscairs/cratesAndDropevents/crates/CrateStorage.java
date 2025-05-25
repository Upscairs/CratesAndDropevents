package dev.upscairs.cratesAndDropevents.crates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrateStorage {

    private FileConfiguration config;
    private File file;

    private final JavaPlugin plugin;
    private final String fileName = "crates.yml";

    public CrateStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save crates.yml!");
            e.printStackTrace();
        }
    }

    // Speichert ein Crate unter dem Namen
    public void saveCrate(Crate crate) {
        config.set("crates." + crate.getName(), crate);
        saveConfig();
    }

    // Entfernt ein Crate nach ID
    public void removeCrate(String id) {
        config.set("crates." + id, null);
        saveConfig();
    }

    // Gibt alle Crate-Namen zurück
    public List<String> getCrateIds() {
        ConfigurationSection section = config.getConfigurationSection("crates");
        if (section == null) return new ArrayList<>();
        return new ArrayList<>(section.getKeys(false));
    }

    // Lädt alle Crates als Liste
    public List<Crate> getAllCrates() {
        List<Crate> list = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("crates");
        if (section == null) return list;

        for (String key : section.getKeys(false)) {
            Object obj = config.get("crates." + key);
            if (obj instanceof Crate) {
                list.add((Crate) obj);
            }
        }
        return list;
    }

    // Lädt ein Crate per ID
    public Crate getCrateById(String id) {
        Object obj = config.get("crates." + id);
        if (obj instanceof Crate) {
            return (Crate) obj;
        }
        return null;
    }
}
