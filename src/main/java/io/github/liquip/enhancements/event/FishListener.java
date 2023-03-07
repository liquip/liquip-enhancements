package io.github.liquip.enhancements.event;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.item.GrapplingHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public record FishListener(@NotNull LiquipEnhancements plugin) implements Listener {
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack mainHand = inventory.getItemInMainHand();
        if (!plugin.getApi()
            .isCustomItemStack(mainHand) || !plugin.getApi()
            .getKeyFromItemStack(mainHand)
            .equals(GrapplingHook.KEY)) {
            return;
        }
        final PlayerFishEvent.State state = event.getState();
        if (state != PlayerFishEvent.State.REEL_IN && state != PlayerFishEvent.State.IN_GROUND) {
            return;
        }
        final Vector velocity = event.getHook()
            .getLocation()
            .subtract(player.getLocation())
            .toVector()
            .normalize()
            .multiply(3);
        velocity.setY(Math.min(Math.abs(velocity.getY() * 0.65), 1));
        player.setVelocity(velocity);
    }
}
