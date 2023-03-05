package io.github.liquip.enhancements.item;

import com.destroystokyo.paper.ParticleBuilder;
import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.util.HashUUID;
import io.github.liquip.paper.core.item.FixedItem;
import io.github.liquip.paper.core.item.feature.minecraft.AttributeModifierFeature;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class StaffOfPower {
    public static final NamespacedKey KEY = new NamespacedKey("liquip", "staff_of_power");
    private static final String TAG = "liquip:staff_of_power";
    private static final double SPEED = 1.5;
    private static final int MAX_LIFE_TICKS = 20 * 10;
    private static final ParticleBuilder PARTICLE = Particle.SONIC_BOOM.builder()
        .count(5)
        .allPlayers();
    private final Set<UUID> uuids;
    private final Set<Ray> rays;

    public StaffOfPower(@NotNull LiquipEnhancements plugin) {
        uuids = new HashSet<>();
        rays = new HashSet<>();
        plugin.getApi()
            .getItemRegistry()
            .register(KEY, new FixedItem.Builder().api(plugin.getApi())
                .key(KEY)
                .material(Material.GOLDEN_SHOVEL)
                .name(Component.text("Staff Of Power")
                    .decoration(TextDecoration.ITALIC, false))
                .taggedFeature(new AttributeModifierFeature(), List.of(Pair.of(Attribute.GENERIC_ATTACK_DAMAGE,
                    new AttributeModifier(HashUUID.md5(TAG), TAG, 80, AttributeModifier.Operation.ADD_NUMBER))))
                .build());
        Bukkit.getScheduler()
            .runTaskTimer(plugin, () -> rays.removeIf(it -> {
                boolean remove = it.run();
                if (remove) {
                    uuids.remove(it.uuid);
                }
                return remove;
            }), 1, 1);
    }

    public void onInteract(@NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR -> {
                if (uuids.contains(player.getUniqueId())) {
                    return;
                }
                final Location eyeLocation = player.getEyeLocation();
                eyeLocation.add(eyeLocation.getDirection()
                    .multiply(3));
                final RayTraceResult result = player.getWorld()
                    .rayTrace(eyeLocation, player.getEyeLocation()
                        .getDirection(), 50, FluidCollisionMode.NEVER, true, 0, null);
                if (result == null) {
                    return;
                }
                final Entity hitEntity = result.getHitEntity();
                if (hitEntity != null) {
                    handleEntity(player, eyeLocation, hitEntity);
                    return;
                }
                final Block hitBlock = result.getHitBlock();
                if (hitBlock == null) {
                    return;
                }
                handleBlock(player, eyeLocation, hitBlock);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (uuids.contains(player.getUniqueId())) {
                    return;
                }
                final Block block = event.getClickedBlock();
                if (block == null) {
                    return;
                }
                handleBlock(player, player.getEyeLocation(), block);
            }
        }
    }

    private void handleEntity(@NotNull Player player, @NotNull Location eyeLocation, @NotNull Entity entity) {
        uuids.add(player.getUniqueId());
        rays.add(new EntityRay(player.getUniqueId(), entity, eyeLocation));
    }

    private void handleBlock(@NotNull Player player, @NotNull Location eyeLocation, @NotNull Block block) {
        uuids.add(player.getUniqueId());
        final Vector movement = block.getLocation()
            .add(.5, .5, .5)
            .subtract(eyeLocation)
            .toVector()
            .normalize()
            .multiply(SPEED);
        rays.add(new BlockRay(player.getUniqueId(), block.getLocation()
            .add(.5, .5, .5), movement, eyeLocation/*.add(movement)*/));
    }

    private static abstract sealed class Ray permits BlockRay, EntityRay {
        private final UUID uuid;

        private Ray(UUID uuid) {
            this.uuid = uuid;
        }

        /**
         * @return whether to remove the ray
         */
        abstract boolean run();
    }

    private static final class BlockRay extends Ray {
        private final Location target;
        private final Vector movement;
        private final Location pos;
        private int lifeTicks;

        private BlockRay(@NotNull UUID uuid, @NotNull Location target, @NotNull Vector movement, @NotNull Location pos) {
            super(uuid);
            this.target = target;
            this.movement = movement;
            this.pos = pos;
            lifeTicks = 0;
        }

        @Override
        public boolean run() {
            lifeTicks++;
            if (lifeTicks > MAX_LIFE_TICKS) {
                return true;
            }
            if (target.distanceSquared(pos) < 2.25) {
                target.getWorld()
                    .spawn(target, LightningStrike.class, it -> {
                        it.setLifeTicks(5);
                        it.setFlashCount(10);
                    });
                return true;
            }
            pos.add(movement);
            PARTICLE.location(pos);
            PARTICLE.spawn();
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (BlockRay) obj;
            return Objects.equals(this.target, that.target) && Objects.equals(this.movement, that.movement) &&
                Objects.equals(this.pos, that.pos) && this.lifeTicks == that.lifeTicks;
        }

        @Override
        public int hashCode() {
            return Objects.hash(target, movement, pos, lifeTicks);
        }
    }

    private static final class EntityRay extends Ray {
        private final Entity target;
        private final Location pos;
        private int lifeTicks;

        private EntityRay(@NotNull UUID uuid, @NotNull Entity target, @NotNull Location pos) {
            super(uuid);
            this.target = target;
            this.pos = pos;
            lifeTicks = 0;
        }

        @Override
        public boolean run() {
            lifeTicks++;
            final Location location = target.getLocation();
            if (lifeTicks > MAX_LIFE_TICKS) {
                return true;
            }
            if (location.distanceSquared(pos) < 2.25) {
                target.getWorld()
                    .spawn(target.getLocation(), LightningStrike.class, it -> {
                        it.setLifeTicks(5);
                        it.setFlashCount(10);
                    });
                return true;
            }
            final Vector movement = location.subtract(pos)
                .toVector()
                .normalize()
                .multiply(SPEED);
            pos.add(movement);
            PARTICLE.location(pos);
            PARTICLE.spawn();
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (EntityRay) obj;
            return Objects.equals(this.target, that.target) && Objects.equals(this.pos, that.pos) &&
                this.lifeTicks == that.lifeTicks;
        }

        @Override
        public int hashCode() {
            return Objects.hash(target, pos, lifeTicks);
        }
    }
}
