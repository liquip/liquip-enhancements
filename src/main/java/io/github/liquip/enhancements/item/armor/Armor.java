package io.github.liquip.enhancements.item.armor;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Armor {
    @NotNull String tag();

    @NotNull NamespacedKey helmet();

    @NotNull NamespacedKey chestplate();

    @NotNull NamespacedKey leggings();

    @NotNull NamespacedKey boots();

    void attachCallback(@NotNull Player player);

    void removeCallback(@NotNull Player player);
}
