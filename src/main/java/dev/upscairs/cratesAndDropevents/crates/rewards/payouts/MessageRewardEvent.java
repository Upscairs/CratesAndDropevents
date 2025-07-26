package dev.upscairs.cratesAndDropevents.crates.rewards.payouts;

import dev.upscairs.cratesAndDropevents.helper.EditMode;
import dev.upscairs.mcGuiFramework.utility.InvGuiUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.CompletableFuture;

public class MessageRewardEvent implements CrateRewardEvent {

    private Component message;

    public MessageRewardEvent(Component message) {
        this.message = message;
    }

    public Component getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    @Override
    public CompletableFuture<Void> execute(Player player, Location location) {

        player.sendMessage(message);

        return CompletableFuture.completedFuture(null);
    }

    public ItemStack getRenderItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(InvGuiUtils.generateDefaultTextComponent("Say ", "#00AAAA").append(message));
        item.setItemMeta(meta);
        return item;
    }

    public EditMode getAssociatedEditMode() {
        return EditMode.EDIT_MESSAGE_EVENT;
    }
}
