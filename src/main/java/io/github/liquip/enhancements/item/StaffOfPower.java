package io.github.liquip.enhancements.item;

import io.github.liquip.api.Liquip;
import io.github.liquip.paper.core.item.FixedItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class StaffOfPower {
    public static final NamespacedKey KEY = new NamespacedKey("liquip", "staff_of_power");
    private final Liquip api;
    private final Set<Void> rays;

    public StaffOfPower(@NotNull Liquip api) {
        this.api = api;
        rays = new HashSet<>();
        api.getItemRegistry()
            .register(KEY, new FixedItem.Builder().api(api)
                .key(KEY)
                .material(Material.GOLDEN_SHOVEL)
                .name(Component.text("Staff Of Power")
                    .decoration(TextDecoration.ITALIC, false))
                .build());
    }

    public void onInteract(@NotNull PlayerInteractEvent event) {
        // TODO
    }
}
