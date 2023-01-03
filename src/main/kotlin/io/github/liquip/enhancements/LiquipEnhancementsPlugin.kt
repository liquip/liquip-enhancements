package io.github.liquip.enhancements

import io.github.liquip.api.LiquipProvider
import io.github.liquip.enhancements.feature.AspectOfTheEnd
import io.github.liquip.enhancements.feature.Jellyfish
import org.bukkit.plugin.java.JavaPlugin

class LiquipEnhancementsPlugin : JavaPlugin() {
    override fun onLoad() {
        val api = LiquipProvider.get()
        AspectOfTheEnd().register(api)
        Jellyfish.PurpleJellyfish(this)
            .register(api)
        Jellyfish.AquaJellyfish(this)
            .register(api)
        Jellyfish.OrangeJellyfish(this)
            .register(api)
    }
}