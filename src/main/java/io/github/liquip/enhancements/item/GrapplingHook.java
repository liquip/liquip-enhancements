package io.github.liquip.enhancements.item;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.paper.core.item.FixedItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record GrapplingHook(@NotNull LiquipEnhancements plugin) {
    public static final NamespacedKey KEY = new NamespacedKey("liquip", "grappling_hook");

    public GrapplingHook {
        plugin.getApi()
            .getItemRegistry()
            .register(KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(KEY)
                .material(Material.FISHING_ROD)
                .name(Component.text("Grappling Hook")
                    .decoration(TextDecoration.ITALIC, false))
                .build());
    }
}
