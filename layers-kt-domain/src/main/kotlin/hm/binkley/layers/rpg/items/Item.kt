package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.set

/** Base type for items. */
abstract class Item<I : Item<I>>(
    name: String,
    /** Item weight in pounds. */
    val weight: Weight,
) : RpgLayer<I>(name) {
    init {
        edit {
            // TODO: Display weight:
            //       - "-" when 0
            //       - no trailing zeros in decimal places
            this["ITEM-WEIGHT"] = weight
        }
    }
}
