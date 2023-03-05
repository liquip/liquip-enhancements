package io.github.liquip.enhancements.item.armor;

import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.util.HashUUID;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.HideDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.LeatherDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.UnbreakableFeature;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class AbyssalDepthSuit implements Armor {
    private static final String TAG = "liquip:armor_sets/abyssaldepthsuit";
    private static final NamespacedKey HELMET_KEY = new NamespacedKey("liquip", "armor/abyssaldepthsuit_helmet");
    private static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/abyssaldepthsuit_chestplate");
    private static final NamespacedKey LEGGINGS_KEY = new NamespacedKey("liquip", "armor/abyssaldepthsuit_leggins");
    private static final NamespacedKey BOOTS_KEY = new NamespacedKey("liquip", "armor/abyssaldepthsuit_boots");
    private static final AttributeModifier MODIFIER =
        new AttributeModifier(HashUUID.md5(TAG), TAG, 18, AttributeModifier.Operation.ADD_NUMBER);
    private final Set<UUID> players = new HashSet<>();

    public AbyssalDepthSuit(@NotNull LiquipEnhancements plugin) {
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
                .name(MiniMessage.miniMessage()
                    .deserialize("<blue>Abyssal Depth Helmet")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(new UnbreakableFeature())
                .feature(new HideDyeFeature())
                .taggedFeature(new LeatherDyeFeature(), 0x050052)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(MiniMessage.miniMessage()
                    .deserialize("<blue>Abyssal Depth Chestplate")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(new UnbreakableFeature())
                .feature(new HideDyeFeature())
                .taggedFeature(new LeatherDyeFeature(), 0x050052)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(LEGGINGS_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(LEGGINGS_KEY)
                .material(Material.LEATHER_LEGGINGS)
                .name(MiniMessage.miniMessage()
                    .deserialize("<blue>Abyssal Depth Leggings")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(new UnbreakableFeature())
                .feature(new HideDyeFeature())
                .taggedFeature(new LeatherDyeFeature(), 0x050052)
                .build());
        plugin.getApi()
            .getItemRegistry()
            .register(BOOTS_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(BOOTS_KEY)
                .material(Material.LEATHER_BOOTS)
                .name(MiniMessage.miniMessage()
                    .deserialize("<blue>Abyssal Depth Boots")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(new UnbreakableFeature())
                .feature(new HideDyeFeature())
                .taggedFeature(new LeatherDyeFeature(), 0x050052)
                .build());
        Bukkit.getScheduler()
            .runTaskTimer(plugin, task -> {
                for (UUID uuid : players) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        players.remove(uuid);
                        continue;
                    }
                    if (!player.isInWater()) {
                        continue;
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 255, true, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 400, 2, true, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 400, 1, true, false, false));
                }
            }, 20, 20);
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
        players.add(player.getUniqueId());
        player.getAttribute(Attribute.GENERIC_ARMOR)
            .addModifier(MODIFIER);
    }

    @Override
    public void removeCallback(@NotNull Player player) {
        players.remove(player.getUniqueId());
        player.getAttribute(Attribute.GENERIC_ARMOR)
            .removeModifier(MODIFIER);
    }
}
