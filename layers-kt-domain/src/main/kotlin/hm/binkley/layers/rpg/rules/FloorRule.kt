package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Layer
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule
import kotlin.math.max

open class FloorRule(
    private val value: Int,
    private val layer: Layer<String, Any, *>,
    private val layers: RpgLayersEditMap,
) : RpgRule<Int>("Floor[Int](value=$value)") {
    override fun invoke(
        key: String,
        values: List<Int>,
        view: Map<String, Any>,
    ): Int = max(value, layers.getAs(key, except = layer))
}
