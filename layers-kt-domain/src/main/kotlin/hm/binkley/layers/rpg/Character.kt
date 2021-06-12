package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.util.stackOf

/**
 * @todo Add a "item rule layer" that defines the key, `WORN` as those items
 *       which are currently `true` for `worn
 */
@Suppress("UNCHECKED_CAST")
open class Character<M : RpgLayer<M>> private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, M>(
    name,
    defaultMutableLayer = { RpgLayer<RpgLayer<*>>(it) as M },
    initLayers = stackOf<M>(PlayerLayer() as M, StatLayer() as M)
) {
    companion object {
        fun character(name: String) = Character<RpgLayer<*>>(name)
    }
}
