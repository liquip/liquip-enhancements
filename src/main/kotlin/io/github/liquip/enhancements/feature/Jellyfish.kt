package io.github.liquip.enhancements.feature

import com.destroystokyo.paper.profile.ProfileProperty
import io.github.liquip.api.item.Item
import io.github.liquip.enhancements.FeatureHelper
import io.github.liquip.enhancements.PlayerStatMap
import java.util.UUID
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.ArmorStand
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

open class Jellyfish(
    private val plugin: Plugin,
    value: String,
    private val texture: String,
    private val cloudColor: Color,
    private val cloudDuration: Int,
    private vararg val cloudEffects: PotionEffect,
) : FeatureHelper {
    private val key = NamespacedKey("liquip", value)
    private val stats = PlayerStatMap()

    override fun getKey(): NamespacedKey = key

    override fun initialize(item: Item) {
        item.registerEvent(PlayerInteractEvent::class.java, this::onInteract)
    }

    private fun onInteract(event: PlayerInteractEvent, item: ItemStack) {
        val player = event.player
        val uuid = player.uniqueId
        if (event.hand != EquipmentSlot.HAND) {
            return
        }
        if (player.openInventory.topInventory.type != InventoryType.CRAFTING) {
            return
        }
        event.isCancelled = true
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (stats.onCooldown(uuid)) {
            player.sendMessage(text("Item is on cooldown"))
            return
        }
        stats.setCooldown(uuid, 1000 * 3)
        val loc = player.eyeLocation
        val jellyfish = loc.world.spawn(loc, ArmorStand::class.java)
        jellyfish.isSmall = true
        jellyfish.isInvisible = true
        jellyfish.isInvulnerable = true
        jellyfish.velocity = loc.direction
        jellyfish.addScoreboardTag("liquip:jellyfish")
        val jellyfishHead = ItemStack(Material.PLAYER_HEAD)
        jellyfishHead.editMeta {
            it as SkullMeta
            val profile = Bukkit.createProfile(UUID.randomUUID())
            profile.setProperty(ProfileProperty("textures", texture))
            it.playerProfile = profile
        }
        jellyfish.setItem(EquipmentSlot.HEAD, jellyfishHead)
        jellyfish.addDisabledSlots(*EquipmentSlot.values())
        item.amount -= 1
        object : BukkitRunnable() {
            override fun run() {
                if (jellyfish.isDead) {
                    cancel()
                }
                if (!jellyfish.isOnGround) {
                    return
                }
                val cloudLoc = jellyfish.location
                val cloud = cloudLoc.world.spawn(cloudLoc, AreaEffectCloud::class.java)
                cloud.duration = cloudDuration
                cloudEffects.forEach { cloud.addCustomEffect(it, true) }
                cloud.color = cloudColor
                jellyfish.remove()
                cancel()
            }
        }.runTaskTimer(plugin, 4, 4)
    }

    class PurpleJellyfish(plugin: Plugin) : Jellyfish(
        plugin, "purple_jellyfish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmI2NTZiYWM2NGFkYTY0ZmEyMjFlOWY0OGZiZjhhYjkzMzRiNzkzNGVmYjk3OWQ3NThkZjRkMGMxYmQxNzY5NSJ9fX0=",
        Color.PURPLE, 60, PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 4), PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, 2),
        PotionEffect(PotionEffectType.POISON, 3 * 20, 4)
    )

    class AquaJellyfish(plugin: Plugin) : Jellyfish(
        plugin, "aqua_jellyfish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQwNzI3MzJkYjcwZmRhZjBiMmFhZTY3YmM0MTViZTE1MzAwYWIzMzZhMTRmMTk5YTRmNDEzMGRiNDM1NTFhMiJ9fX0=",
        Color.AQUA, 10, PotionEffect(PotionEffectType.DOLPHINS_GRACE, 15 * 20, 1),
        PotionEffect(PotionEffectType.WATER_BREATHING, 10 * 20, 2), PotionEffect(PotionEffectType.SPEED, 15 * 20, 2)
    )

    class OrangeJellyfish(plugin: Plugin) : Jellyfish(
        plugin, "orange_jellyfish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI2MTcyODE5MmI3ZDU5NmQwZTdkYjg2YjkzM2NlYWMyNmQwYzg1MDIwNmU3NDljZmNlYTg2NjM1OTMyNzFjMyJ9fX0=",
        Color.ORANGE, 60, PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 2),
        PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 0), PotionEffect(PotionEffectType.SATURATION, 3 * 20, 2)
    )
}