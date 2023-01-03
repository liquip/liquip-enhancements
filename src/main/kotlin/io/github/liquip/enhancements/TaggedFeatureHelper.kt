package io.github.liquip.enhancements

import io.github.liquip.api.Liquip
import io.github.liquip.api.item.TaggedFeature

interface TaggedFeatureHelper<T> : TaggedFeature<T> {
    fun register(api: Liquip) {
        api.taggedFeatureRegistry.register(this.key, this)
    }
}