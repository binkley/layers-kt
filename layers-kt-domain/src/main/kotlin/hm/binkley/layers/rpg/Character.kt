package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.util.stackOf

@Suppress("UNCHECKED_CAST")
open class Character<M : RpgLayer<M>> private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, M>(
    name,
    initLayers = stackOf<M>(PlayerLayer() as M, StatLayer() as M),
    defaultMutableLayer = { RpgLayer<RpgLayer<*>>(it) as M }
) {
    companion object {
        fun character(name: String) = Character<RpgLayer<*>>(name)
    }
}
