package io.github.liquip.enhancements.item.armor;

import io.github.liquip.api.Liquip;
import io.github.liquip.enhancements.util.HashUUID;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.HideDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.LeatherDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.UnbreakableFeature;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record CelestialPlate(@NotNull Liquip api) implements ArmorPiece {
    private static final String TAG = "liquip:armor_sets/celestial";
    private static final AttributeModifier MODIFIER =
        new AttributeModifier(HashUUID.md5(TAG), TAG, 20, AttributeModifier.Operation.ADD_NUMBER);
    private static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/liquip_chestplate");
    private static final UnbreakableFeature UNBREAKABLE_FEATURE = new UnbreakableFeature();
    private static final HideDyeFeature HIDE_DYE_FEATURE = new HideDyeFeature();
    private static final LeatherDyeFeature LEATHER_DYE_FEATURE = new LeatherDyeFeature();

    public CelestialPlate {
        api.getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(api)
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(MiniMessage.miniMessage()
                    .deserialize("<aqua>Celestial Plate")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(UNBREAKABLE_FEATURE)
                .feature(HIDE_DYE_FEATURE)
                .taggedFeature(LEATHER_DYE_FEATURE, 0xb0f7ff)
                .build());
    }

    @Override
    public @NotNull String tag() {
        return TAG;
    }

    @Override
    public @NotNull NamespacedKey key() {
        return CHESTPLATE_KEY;
    }

    @Override
    public void attachCallback(@NotNull Player player) {

    }

    @Override
    public void removeCallback(@NotNull Player player) {

    }
}
