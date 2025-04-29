package dev.upscairs.cratesAndDropevents.configs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatMessageConfig {

    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration config;

    private final Map<String, String> defaultMessages = new HashMap<>();

    public ChatMessageConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        setupDefaults();
        load();
    }

    private void setupDefaults() {
        defaultMessages.put("dropevent.broadcast.local.countdown", "&5A dropevent starts here in %ts.&r");
        defaultMessages.put("dropevent.broadcast.local.start", "&5The dropevent started.&r");
        defaultMessages.put("dropevent.broadcast.local.end", "&5The dropevent has ended.&r");
        defaultMessages.put("dropevent.broadcast.global.countdown", "&5A dropevent starts in %ts at %c (%w) %l. %p&r");
        defaultMessages.put("dropevent.broadcast.tp-prompt.chat", "&4&l[Teleport]&r");
        defaultMessages.put("dropevent.broadcast.tp-prompt.hover", "&5Click to teleport to %l!&r");

        defaultMessages.put("dropevent.success.teleported", "&5Teleporting...&r");
        defaultMessages.put("dropevent.success.all-stopped", "&2All events have been stopped.&r");
        defaultMessages.put("dropevent.success.created", "&2Event has been created.&r");
        defaultMessages.put("dropevent.success.removed", "&2Event has been removed.&r");
        defaultMessages.put("dropevent.success.setting-changed", "&2Setting changed successfully.&r");

        defaultMessages.put("dropevent.error.unknown-id", "&4Unknown event ID.&r");
        defaultMessages.put("dropevent.error.event-over", "&4This event is over.&r");
        defaultMessages.put("dropevent.error.not-teleportable", "&4This event cannot be teleported to.&r");
        defaultMessages.put("dropevent.error.already-teleported", "&4You can only port once to an event.&r");
        defaultMessages.put("dropevent.error.simultanous-limit", "&4There are too many active events right now.&r");
        defaultMessages.put("dropevent.error.ownable", "&4You don't have the permission to start an event.&r");
        defaultMessages.put("dropevent.error.player-amount", "&4There must be at least %p players online to start an event.&r");
        defaultMessages.put("dropevent.error.missing-name", "&4You must specify a name.&r");
        defaultMessages.put("dropevent.error.name-not-found", "&4There is no event with that name.&r");
        defaultMessages.put("dropevent.error.name-already-exists", "&4There is already an event with that name.&r");
        defaultMessages.put("dropevent.error.name-no-spaces", "&4The name must not contain spaces.&r");
        defaultMessages.put("dropevent.error.setting-update-failed", "&4Failed to update setting.&r");

        defaultMessages.put("system.command.error.not-found", "&4Command not found&r");

    }

    public void load() {
        file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        // Set defaults if missing
        for (String key : defaultMessages.keySet()) {
            if (!config.contains(key)) {
                config.set(key, defaultMessages.get(key));
            }
        }

        save();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return config.getString(key, "");
    }

    public TextComponent getColored(String key) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(get(key));
    }

}
