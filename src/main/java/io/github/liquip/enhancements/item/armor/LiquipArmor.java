package io.github.liquip.enhancements.item.armor;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.util.HashUUID;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.HideDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.LeatherDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.UnbreakableFeature;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record LiquipArmor(@NotNull LiquipEnhancements plugin) implements Armor {
    private static final String TAG = "liquip:armor_sets/liquip";
    private static final AttributeModifier MODIFIER =
        new AttributeModifier(HashUUID.md5(TAG), TAG, 20, AttributeModifier.Operation.ADD_NUMBER);
    private static final NamespacedKey HELMET_KEY = new NamespacedKey("liquip", "armor/liquip_helmet");
    private static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/liquip_chestplate");
    private static final NamespacedKey LEGGINGS_KEY = new NamespacedKey("liquip", "armor/liquip_leggings");
    private static final NamespacedKey BOOTS_KEY = new NamespacedKey("liquip", "armor/liquip_boots");
    private static final UnbreakableFeature UNBREAKABLE_FEATURE = new UnbreakableFeature();
    private static final HideDyeFeature HIDE_DYE_FEATURE = new HideDyeFeature();
    private static final LeatherDyeFeature LEATHER_DYE_FEATURE = new LeatherDyeFeature();

    public LiquipArmor {
        plugin.getTags()
            .put(TAG, player -> {
                player.removeScoreboardTag(TAG);
                removeCallback(player);
            });
        plugin.getApi()
            .getItemRegistry()
            .register(HELMET_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(HELMET_KEY)
                .material(Material.LEATHER_HELMET)
                .name(Component.text("Liquip Helmet")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0xFFFFFF)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(Component.text("Liquip Chestplate")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0xFFFFFF)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(LEGGINGS_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(LEGGINGS_KEY)
                .material(Material.LEATHER_LEGGINGS)
                .name(Component.text("Liquip Leggings")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0xFFFFFF)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(BOOTS_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(BOOTS_KEY)
                .material(Material.LEATHER_BOOTS)
                .name(Component.text("Liquip Boots")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0xFFFFFF)
                .build());
    }

    @Override
    public @NotNull String tag() {
        return TAG;
    }

    @Override
    public @NotNull NamespacedKey helmet() {
        return HELMET_KEY;
    }

    @Override
    public @NotNull NamespacedKey chestplate() {
        return CHESTPLATE_KEY;
    }

    @Override
    public @NotNull NamespacedKey leggings() {
        return LEGGINGS_KEY;
    }

    @Override
    public @NotNull NamespacedKey boots() {
        return BOOTS_KEY;
    }

    @Override
    public void attachCallback(@NotNull Player player) {
        player.getAttribute(Attribute.GENERIC_ARMOR)
            .addModifier(MODIFIER);
    }

    @Override
    public void removeCallback(@NotNull Player player) {
        player.getAttribute(Attribute.GENERIC_ARMOR)
            .removeModifier(MODIFIER);
    }
}
