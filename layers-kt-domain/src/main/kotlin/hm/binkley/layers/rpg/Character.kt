package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.util.stackOf

/**
 * @todo Add a "item rule layer" that defines the key, `WORN` as those items
 *       which are currently `true` for `worn
 */
@Suppress("UNCHECKED_CAST")
open class Character<M : RpgLayer<M>> private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, M>(
    name,
    defaultMutableLayer = {
        @Suppress("UPPER_BOUND_VIOLATED_WARNING")
        RpgLayer<RpgLayer<*>>(it) as M
    },
    initLayers = stackOf(
        PlayerLayer() as M,
        StatLayer() as M,
        InventoryLayer() as M,
    )
) {
    companion object {
        @Suppress("UPPER_BOUND_VIOLATED_WARNING")
        fun character(name: String) = Character<RpgLayer<*>>(name)
    }
}
