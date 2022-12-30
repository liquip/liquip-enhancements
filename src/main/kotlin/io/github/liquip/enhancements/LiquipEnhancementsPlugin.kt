package io.github.liquip.enhancements

import io.github.liquip.api.LiquipProvider
import io.github.liquip.enhancements.feature.AspectOfTheEnd
import org.bukkit.plugin.java.JavaPlugin

class LiquipEnhancementsPlugin : JavaPlugin() {
    override fun onEnable() {
        val api = LiquipProvider.get()
        AspectOfTheEnd().register(api)
    }
}