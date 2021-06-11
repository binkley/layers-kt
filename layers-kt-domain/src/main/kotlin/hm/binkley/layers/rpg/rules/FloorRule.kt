package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.items.WearableItem
import kotlin.math.max

open class FloorRule(
    private val value: Int,
    private val layer: WearableItem<*>,
) : RpgRule<Int>("Floor[Int](value=$value)") {
    override fun invoke(
        key: String,
        values: List<Int>,
        layers: RpgLayers,
    ): Int = max(value, layers.getAs(key, except = layer.same()))
}
