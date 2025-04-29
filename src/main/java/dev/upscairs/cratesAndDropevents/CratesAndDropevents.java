package dev.upscairs.cratesAndDropevents;

import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.DropeventCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.cratesAndDropevents.gui.functional.GuiInteractionHandler;
import dev.upscairs.cratesAndDropevents.helper.EventDragonDropPreventListener;
import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CratesAndDropevents extends JavaPlugin {

    private static ChatMessageConfig chatMessageConfig;

    @Override
    public void onEnable() {

        ConfigurationSerialization.registerClass(Dropevent.class);

        DropeventStorage.init(this);

        registerCommands();
        registerEvents();
        registerConfigs();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("dropevent").setExecutor(new DropeventCommand(this));
        getCommand("dropevent").setTabCompleter(new DropeventCommand(this));

        getCommand("de").setExecutor(new DropeventCommand(this));
        getCommand("de").setTabCompleter(new DropeventCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new GuiInteractionHandler(), this);
        getServer().getPluginManager().registerEvents(new EventDragonDropPreventListener(), this);
    }

    private void registerConfigs() {
        chatMessageConfig = new ChatMessageConfig(this);
        configureDefaultConfig();
    }

    public void configureDefaultConfig() {
        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        saveDefaultConfig();

        getConfig().addDefault("dropevents.simultanous-limit.active", false);
        getConfig().addDefault("dropevents.simultanous-limit.count", 10);
        getConfig().addDefault("dropevents.normal-players.ownable", false);
        getConfig().addDefault("dropevents.normal-players.start.online-player-condition", false);
        getConfig().addDefault("dropevents.normal-players.start.min-online-players", 10);

        getConfig().options().copyDefaults(true);

        saveConfig();
    }

    public ChatMessageConfig getChatMessageConfig() {
        return chatMessageConfig;
    }


}
