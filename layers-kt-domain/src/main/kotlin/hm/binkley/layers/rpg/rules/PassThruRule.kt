package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Layer
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.RpgView

/**
 * A "pass through" rule has no behavior, representing inactive rules, however
 * it keeps the name of the rules that would otherwise be active.  This lets
 * text representations show the rule properly and if that rule is active or
 * inactive.
 */
class PassThruRule<T : Any>(
    name: String,
    private val layer: Layer<String, Any, *>,
    private val layers: RpgLayersEditMap,
) : RpgRule<T>(name) {
    override fun invoke(
        key: String,
        values: List<T>,
        view: RpgView,
    ): T = layers.getAs(key, except = layer)
}
