package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.Rule
import hm.binkley.layers.rpg.items.Weight
import hm.binkley.layers.rpg.items.sum

/** Base type for rule of total item weight. */
object TotalWeightRule : Rule<String, Weight, Weight>("Total weight") {
    override fun invoke(
        key: String,
        values: Sequence<Weight>,
        layers: Layers<String, Weight, *>,
    ): Weight = values.sum()
}
