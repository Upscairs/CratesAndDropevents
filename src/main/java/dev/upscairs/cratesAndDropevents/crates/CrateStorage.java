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

    private static FileConfiguration config;
    private static File file;
    private static final String fileName = "crates.yml";

    public static void init(JavaPlugin plugin) {

        //Create folder, if not existing
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Konnte " + fileName + " nicht erstellen!");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     *
     * Saves a crate to the config file. If the crate already exists, it will be overwritten.
     * The name of the crate will be used as the key in the config file.
     *
     * @param crate
     */
    public static void saveCrate(Crate crate) {
        config.set("crates." + crate.getName(), crate);
        saveFile();
    }

    /**
     *
     * Deletes a crate from the file.
     *
     * @param crate
     */
    public static void removeCrate(Crate crate) {
        config.set("crates." + crate.getName(), null);
        saveFile();
    }

    /**
     *
     * Returns a list of all crate names in the config file.
     *
     * @return
     */
    public static List<String> getCrateNames() {
        ConfigurationSection section = config.getConfigurationSection("crates");
        List<String> keys = new ArrayList<>();
        if (section != null) {
            keys.addAll(section.getKeys(false));
        }
        return keys;
    }

    /**
     *
     * Returns a list of all crates in the config file.
     *
     * @return
     */
    public static List<Crate> getAll() {
        List<Crate> list = new ArrayList<>();
        if (config.contains("crates")) {
            ConfigurationSection section = config.getConfigurationSection("crates");
            for (String key : section.getKeys(false)) {
                Object obj = config.get("crates." + key);
                if (obj instanceof Crate) {
                    list.add((Crate) obj);
                }
            }
        }
        return list;
    }

    /**
     *
     * Looks up a crate by its name in the config file. Returns null if the crate does not exist.
     *
     * @param name
     * @return
     */
    public static Crate getCrateByName(String name) {
        if (config.contains("crates." + name)) {
            Object obj = config.get("crates." + name);
            if (obj instanceof Crate) {
                return (Crate) obj;
            }
        }
        return null;
    }

    /**
     *
     * Saves the config file to disk.
     *
     */
    private static void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
