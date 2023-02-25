package io.github.liquip.enhancements.item.armor;

import io.github.liquip.api.Liquip;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.LeatherDyeFeature;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record LiquipArmor(@NotNull Liquip api) {
    public static final NamespacedKey HELMET_KEY = new NamespacedKey("liquip", "armor/liquip_helmet");
    public static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/liquip_chestplate");
    public static final NamespacedKey LEGGINGS_KEY = new NamespacedKey("liquip", "armor/liquip_leggings");
    public static final NamespacedKey BOOTS_KEY = new NamespacedKey("liquip", "armor/liquip_boots");

    public void register() {
        final LeatherDyeFeature leatherDyeFeature = new LeatherDyeFeature();
        api.getItemRegistry()
            .register(HELMET_KEY, new FixedItem.Builder().api(api)
                .key(HELMET_KEY)
                .material(Material.LEATHER_HELMET)
                .name(Component.text("Liquip Helmet")
                    .decoration(TextDecoration.ITALIC, false))
                .taggedFeature(leatherDyeFeature, 0xFFFFFF)
                .build());
        api.getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(api)
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(Component.text("Liquip Chestplate")
                    .decoration(TextDecoration.ITALIC, false))
                .taggedFeature(leatherDyeFeature, 0xFFFFFF)
                .build());
        api.getItemRegistry()
            .register(LEGGINGS_KEY, new FixedItem.Builder().api(api)
                .key(LEGGINGS_KEY)
                .material(Material.LEATHER_LEGGINGS)
                .name(Component.text("Liquip Leggings")
                    .decoration(TextDecoration.ITALIC, false))
                .taggedFeature(leatherDyeFeature, 0xFFFFFF)
                .build());
        api.getItemRegistry()
            .register(BOOTS_KEY, new FixedItem.Builder().api(api)
                .key(BOOTS_KEY)
                .material(Material.LEATHER_BOOTS)
                .name(Component.text("Liquip Boots")
                    .decoration(TextDecoration.ITALIC, false))
                .taggedFeature(leatherDyeFeature, 0xFFFFFF)
                .build());
    }
}
