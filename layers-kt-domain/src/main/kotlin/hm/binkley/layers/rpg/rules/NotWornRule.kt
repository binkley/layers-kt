package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.items.WearableItem

/**
 * This "pass through" rule has no behavior, representing unapplied rules,
 * however it keeps the name of the rules that would otherwise be applied.
 * This lets text representations show the rule properly, and if that rule is
 * applicable or not.
 */
class NotWornRule<T : Any>(
    name: String,
    private val layer: WearableItem<*>,
) : RpgRule<T>(name) {
    override fun invoke(
        key: String,
        values: List<T>,
        layers: RpgLayers,
    ): T = layers.getAs(key, except = layer.same())
}
