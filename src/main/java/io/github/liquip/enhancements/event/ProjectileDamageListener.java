package io.github.liquip.enhancements.event;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.item.armor.CelestialPlate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ProjectileDamageListener(@NotNull LiquipEnhancements plugin) implements Listener {
    @EventHandler
    public void onDamage(ProjectileHitEvent event) {
        if (!(event.getHitEntity() instanceof Player player)) {
            return;
        }
        final ItemStack chestplate = player.getInventory()
            .getChestplate();
        if (chestplate == null || !plugin.getApi()
            .isCustomItemStack(chestplate) || !plugin.getApi()
            .getKeyFromItemStack(chestplate)
            .equals(CelestialPlate.CHESTPLATE_KEY)) {
            return;
        }
        event.setCancelled(true);
    }
}
