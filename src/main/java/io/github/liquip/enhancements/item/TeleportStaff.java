package io.github.liquip.enhancements.item;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.paper.core.item.FixedItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public record TeleportStaff(@NotNull LiquipEnhancements plugin) {
    public static final NamespacedKey KEY = new NamespacedKey("liquip", "teleport_staff");
    private static final Vector Y_ONE = new Vector(0, 1, 0);

    public TeleportStaff {
        plugin.getApi()
            .getItemRegistry()
            .register(KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(KEY)
                .material(Material.STICK)
                .name(Component.text("Teleport Staff")
                    .decoration(TextDecoration.ITALIC, false))
                .build());
    }

    public void onInteract(@NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final Location eyeLocation = player.getEyeLocation();
        player.teleport(eyeLocation.add(eyeLocation.getDirection()
            .multiply(8)
            .subtract(Y_ONE)));
    }
}
