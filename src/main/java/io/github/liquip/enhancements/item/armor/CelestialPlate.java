package io.github.liquip.enhancements.item.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.HideDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.LeatherDyeFeature;
import io.github.liquip.paper.core.item.feature.minecraft.UnbreakableFeature;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class CelestialPlate implements ArmorPiece {
    public static final NamespacedKey CHESTPLATE_KEY = new NamespacedKey("liquip", "armor/celestial_plate");
    private static final String TAG = "liquip:armor_sets/celestial";
    private final Set<UUID> players = new HashSet<>();

    public CelestialPlate(@NotNull LiquipEnhancements plugin) {
        plugin.getTags()
            .put(TAG, player -> player.removeScoreboardTag(TAG));
        plugin.getApi()
            .getItemRegistry()
            .register(CHESTPLATE_KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(CHESTPLATE_KEY)
                .material(Material.LEATHER_CHESTPLATE)
                .name(MiniMessage.miniMessage()
                    .deserialize("<aqua>Celestial Plate")
                    .decoration(TextDecoration.ITALIC, false))
                .feature(new UnbreakableFeature())
                .feature(new HideDyeFeature())
                .taggedFeature(new LeatherDyeFeature(), 0xb0f7ff)
                .build());
        Bukkit.getScheduler()
            .runTaskTimer(plugin, task -> {
                for (UUID uuid : players) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        players.remove(uuid);
                        continue;
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 255, true, false, false));
                }
            }, 20, 20);
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
    public @NotNull PlayerArmorChangeEvent.SlotType slot() {
        return PlayerArmorChangeEvent.SlotType.CHEST;
    }

    @Override
    public void attachCallback(@NotNull Player player) {
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(true);
        players.add(player.getUniqueId());
    }

    @Override
    public void removeCallback(@NotNull Player player) {
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        player.setAllowFlight(false);
        players.remove(player.getUniqueId());
    }
}
