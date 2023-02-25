package io.github.liquip.enhancements.item;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.item.Feature;
import io.github.liquip.api.item.Item;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class StaffOfPower implements Feature {
    private final NamespacedKey key = new NamespacedKey("liquip", "staff_of_power");

    public void register(@NotNull Liquip api) {
        api.getFeatureRegistry()
            .register(key, this);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @Override
    public void initialize(@NonNull Item item) {
        // TODO
    }

    @Override
    public void apply(@NonNull Item item, @NonNull ItemStack itemStack) {
        // TODO
    }
}
