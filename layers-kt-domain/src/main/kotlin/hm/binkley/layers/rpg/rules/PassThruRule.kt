package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Layer
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule

open class PassThruRule<T : Any>(
    private val layer: Layer<String, Any, *>,
    private val layers: RpgLayersEditMap,
) : RpgRule<T>("Pass-Thru") {
    override fun invoke(
        key: String,
        values: List<T>,
        view: Map<String, Any>,
    ): T = layers.getAs(key, except = layer)
}
