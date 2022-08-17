package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.items.Weight
import hm.binkley.layers.rpg.items.sum

/**
 * This layer tracks character inventory.
 * Supported keys are:
 * - "ITEM-WEIGHT" &mdash; the total weight of all worn items
 */
class InventoryLayer : RpgLayer<InventoryLayer>("Inventory") {
    init {
        edit {
            this["ITEM-WEIGHT"] = rule<Weight>("Sum[Float]") { _, values, _ ->
                values.sum()
            }
        }
    }
}
