package io.github.liquip.enhancements.event;

import io.github.liquip.api.Liquip;
import io.github.liquip.enhancements.item.armor.CelestialPlate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ProjectileDamageListener(@NotNull Liquip api) implements Listener {
    @EventHandler
    public void onDamage(ProjectileHitEvent event) {
        if (!(event.getHitEntity() instanceof Player player)) {
            return;
        }
        final ItemStack chestplate = player.getInventory()
            .getChestplate();
        if (chestplate == null || !api.isCustomItemStack(chestplate) || !api.getKeyFromItemStack(chestplate)
            .equals(CelestialPlate.CHESTPLATE_KEY)) {
            return;
        }
        event.setCancelled(true);
    }
}
