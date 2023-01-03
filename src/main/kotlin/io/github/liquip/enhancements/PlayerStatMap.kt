package io.github.liquip.enhancements

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import java.util.UUID

class PlayerStatMap {
    private val cooldown = Object2LongOpenHashMap<UUID>()

    fun setCooldown(uuid: UUID, millis: Long) {
        val now = System.currentTimeMillis()
        cooldown[uuid] = now + millis
    }

    fun onCooldown(uuid: UUID): Boolean = cooldown.getLong(uuid) > System.currentTimeMillis()
}