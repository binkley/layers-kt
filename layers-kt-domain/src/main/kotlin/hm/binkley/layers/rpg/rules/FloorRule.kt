package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.items.Wearable
import kotlin.math.max

/** Base type for rules returning a minimum value. */
class FloorRule(
    private val value: Int,
    private val layer: Wearable<*>,
) : RpgRule<Int>("Floor[Int](value=$value)") {
    override fun invoke(
        key: String,
        values: Sequence<Int>,
        layers: RpgLayers,
    ): Int = max(value, layers.getAs(key, except = layer.versions()))
}
