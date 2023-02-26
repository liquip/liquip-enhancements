package io.github.liquip.enhancements.event;

import io.github.liquip.api.Liquip;
import net.kyori.adventure.key.Key;
import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
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
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR -> {
                final RayTraceResult result = player.getWorld()
                    .rayTrace(player.getEyeLocation(), player.getEyeLocation()
                        .getDirection(), 20, FluidCollisionMode.NEVER, true, 0.1, null);
                if (result == null) {
                    return;
                }
                final Entity hitEntity = result.getHitEntity();
                if (hitEntity != null) {
                    handleEntity(hitEntity);
                    return;
                }
                final Block hitBlock = result.getHitBlock();
                if (hitBlock == null) {
                    return;
                }
                handleBlock(hitBlock);
            }
            case RIGHT_CLICK_BLOCK -> {
                final Block block = event.getClickedBlock();
                if (block == null) {
                    return;
                }
                handleBlock(block);
            }
        }
    }

    private void handleEntity(@NotNull Entity entity) {

    }

    private void handleBlock(@NotNull Block block) {

    }
}
