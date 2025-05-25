package dev.upscairs.cratesAndDropevents;

import dev.upscairs.cratesAndDropevents.configs.ChatMessageConfig;
import dev.upscairs.cratesAndDropevents.crates.Crate;
import dev.upscairs.cratesAndDropevents.crates.CrateCommand;
import dev.upscairs.cratesAndDropevents.crates.CrateStorage;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateReward;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateRewardCommand;
import dev.upscairs.cratesAndDropevents.crates.rewards.CrateRewardStorage;
import dev.upscairs.cratesAndDropevents.dropevents.Dropevent;
import dev.upscairs.cratesAndDropevents.dropevents.commands.DropeventCommand;
import dev.upscairs.cratesAndDropevents.dropevents.management.DropeventStorage;
import dev.upscairs.cratesAndDropevents.helper.EventDragonDropPreventListener;
import dev.upscairs.mcGuiFramework.McGuiFramework;
import dev.upscairs.mcGuiFramework.functionality.GuiInteractionHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CratesAndDropevents extends JavaPlugin {

    private static ChatMessageConfig chatMessageConfig;

    public final NamespacedKey EVENT_KEY = new NamespacedKey(this,"DROPEVENT_ITEM");
    public final NamespacedKey CRATE_KEY = new NamespacedKey(this,"CRATE");

    @Override
    public void onEnable() {

        ConfigurationSerialization.registerClass(Dropevent.class, "Dropevent");
        ConfigurationSerialization.registerClass(Crate.class, "Crate");
        ConfigurationSerialization.registerClass(CrateReward.class, "CrateReward" );

        DropeventStorage.init(this);
        CrateStorage.init(this);
        CrateRewardStorage.init(this);

        registerCommands();
        registerEvents();
        registerConfigs();

        McGuiFramework.initalize(this);

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

        getCommand("crate").setExecutor(new CrateCommand(this));
        getCommand("crate").setTabCompleter(new CrateCommand(this));

        getCommand("reward").setExecutor(new CrateRewardCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new GuiInteractionHandler(), this);
        getServer().getPluginManager().registerEvents(new EventDragonDropPreventListener(), this);
        getServer().getPluginManager().registerEvents(new DropeventItemHandler(this), this);
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

        getConfig().addDefault("dropevents.simultaneous-limit.active", false);
        getConfig().addDefault("dropevents.simultaneous-limit.count", 5);
        getConfig().addDefault("dropevents.normal-players.usable", false);
        getConfig().addDefault("dropevents.normal-players.start.online-player-condition", false);
        getConfig().addDefault("dropevents.normal-players.start.min-online-players", 10);

        getConfig().options().copyDefaults(true);

        saveConfig();
    }

    public ChatMessageConfig getChatMessageConfig() {
        return chatMessageConfig;
    }


}
