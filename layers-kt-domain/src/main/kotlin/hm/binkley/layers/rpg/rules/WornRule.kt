package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Rule
import hm.binkley.layers.rpg.RpgLayers
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.items.Wearable

/**
 * This "pass through" rule has no behavior, representing unapplied rules,
 * however it keeps the name of the rules that would otherwise be applied.
 * This lets text representations show the rule properly, and if that rule is
 * applicable or not.
 */
class WornRule<T : Any>(
    name: String,
    private val rule: Rule<String, Any, T>,
    private val layer: Wearable<*>,
) : RpgRule<T>(name) {
    override fun invoke(
        key: String,
        values: Sequence<T>,
        layers: RpgLayers,
    ): T =
        if (layer.worn) rule(key, values, layers)
        else layers.getAs(key, except = layer.same())
}
