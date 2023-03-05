package io.github.liquip.enhancements.item.armor;

import io.github.liquip.api.Liquip;
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

public record TitaniumArmor(@NotNull Liquip api) implements Armor {
    private static final String TAG = "liquip:armor_sets/titanium";
    private static final AttributeModifier MODIFIER =
        new AttributeModifier(HashUUID.md5(TAG), TAG, 20, AttributeModifier.Operation.ADD_NUMBER);
    private static final NamespacedKey HELMET_KEY = new NamespacedKey("liquip", "armor/titanium_helmet");
    private static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/titanium_chestplate");
    private static final NamespacedKey LEGGINGS_KEY = new NamespacedKey("liquip", "armor/titanium_leggings");
    private static final NamespacedKey BOOTS_KEY = new NamespacedKey("liquip", "armor/titanium_boots");
    private static final UnbreakableFeature UNBREAKABLE_FEATURE = new UnbreakableFeature();
    private static final HideDyeFeature HIDE_DYE_FEATURE = new HideDyeFeature();
    private static final LeatherDyeFeature LEATHER_DYE_FEATURE = new LeatherDyeFeature();

    public TitaniumArmor {
        api.getItemRegistry()
            .register(HELMET_KEY, new FixedItem.Builder().api(api)
                .key(HELMET_KEY)
                .material(Material.LEATHER_HELMET)
                .name(Component.text("Titanium Helmet")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0x878681)
                .build());
        api.getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(api)
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(Component.text("Titanium Chestplate")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0x878681)
                .build());
        api.getItemRegistry()
            .register(LEGGINGS_KEY, new FixedItem.Builder().api(api)
                .key(LEGGINGS_KEY)
                .material(Material.LEATHER_LEGGINGS)
                .name(Component.text("Titanium Leggings")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0x878681)
                .build());
        api.getItemRegistry()
            .register(BOOTS_KEY, new FixedItem.Builder().api(api)
                .key(BOOTS_KEY)
                .material(Material.LEATHER_BOOTS)
                .name(Component.text("Titanium Boots")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0x878681)
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
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)
            .addModifier(MODIFIER);
    }

    @Override
    public void removeCallback(@NotNull Player player) {
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)
            .removeModifier(MODIFIER);
    }
}
