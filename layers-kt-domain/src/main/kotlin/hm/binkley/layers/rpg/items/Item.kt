package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.toValue

abstract class Item<I : Item<I>>(
    name: String,
    val weight: Float,
) : RpgLayer<I>(name) {
    init {
        edit {
            this["ITEM-WEIGHT"] = weight.toValue()
        }
    }
}
