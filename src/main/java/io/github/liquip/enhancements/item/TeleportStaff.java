package io.github.liquip.enhancements.item;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.item.Feature;
import io.github.liquip.api.item.Item;
import io.github.liquip.paper.core.item.FixedItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class TeleportStaff implements Feature {
    private final NamespacedKey key = new NamespacedKey("liquip", "teleport_staff");
    private final Vector yOne = new Vector(0, 1, 0);

    public void register(@NotNull Liquip api) {
        api.getItemRegistry()
            .register(key, new FixedItem.Builder().api(api)
                .key(key)
                .material(Material.STICK)
                .name(Component.text("Teleport Staff"))
                .feature(this)
                .build());
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @Override
    public void initialize(@NonNull Item item) {
        item.registerEvent(PlayerInteractEvent.class, this::onInteract);
    }

    private void onInteract(PlayerInteractEvent event, ItemStack stack) {
        final Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final Location eyeLocation = player.getEyeLocation();
        player.teleport(eyeLocation.add(eyeLocation.getDirection()
            .multiply(8)
            .subtract(yOne)));
    }
}
