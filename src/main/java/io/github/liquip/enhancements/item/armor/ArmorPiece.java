package io.github.liquip.enhancements.item.armor;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ArmorPiece {
    @NotNull String tag();

    @NotNull NamespacedKey key();

    void attachCallback(@NotNull Player player);

    void removeCallback(@NotNull Player player);
}
