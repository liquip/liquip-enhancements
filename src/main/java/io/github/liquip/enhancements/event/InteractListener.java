package io.github.liquip.enhancements.event;

import io.github.liquip.api.Liquip;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public record InteractListener(@NotNull Liquip api, @NotNull Map<Key, Consumer<PlayerInteractEvent>> callbacks)
    implements Listener {
    @EventHandler
    public void onInteract(@NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory()
            .getItemInMainHand();
        if (!api.isCustomItemStack(item)) {
            return;
        }
        final Key key = api.getKeyFromItemStack(item);
        final Consumer<PlayerInteractEvent> callback = callbacks.get(key);
        if (callback == null) {
            return;
        }
        callback.accept(event);
    }
}
