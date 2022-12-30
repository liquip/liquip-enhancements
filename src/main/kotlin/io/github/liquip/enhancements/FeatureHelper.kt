package io.github.liquip.enhancements

import io.github.liquip.api.Liquip
import io.github.liquip.api.item.Feature

interface FeatureHelper : Feature {
    fun register(api: Liquip) {
        api.featureRegistry.register(this.key, this)
    }
}