package io.github.liquip.enhancements.feature

import io.github.liquip.api.item.Item
import io.github.liquip.enhancements.FeatureHelper
import org.bukkit.NamespacedKey
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class AspectOfTheEnd : FeatureHelper {
    private val key = NamespacedKey("liquip", "aspect_of_the_end")

    override fun getKey(): NamespacedKey = key

    override fun initialize(item: Item) {
        item.registerEvent(PlayerInteractEvent::class.java) { event, _ ->
            val player = event.player
            if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) {
                return@registerEvent
            }
            val eyeLocation = player.eyeLocation
            player.teleport(
                eyeLocation.add(eyeLocation.direction.multiply(8)
                                    .apply { y -= 1 })
            )
        }
    }
}