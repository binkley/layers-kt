package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.toValue

abstract class Item<I : Item<I>>(
    name: String,
    val weight: Float, // TODO: BigDecimal with 2 decimal places
) : RpgLayer<I>(name) {
    init {
        edit {
            // TODO: Display weight:
            //       - "-" when 0
            //       - no trailing zeros in decimal places
            this["ITEM-WEIGHT"] = weight.toValue()
        }
    }
}
