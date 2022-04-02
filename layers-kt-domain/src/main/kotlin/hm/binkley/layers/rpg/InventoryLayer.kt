package hm.binkley.layers.rpg

/**
 * This layer tracks character inventory.
 * Supported keys are:
 * - "ITEM-WEIGHT" &mdash; the total weight of all worn items
 */
class InventoryLayer : RpgLayer<InventoryLayer>("Inventory") {
    init {
        edit {
            this["ITEM-WEIGHT"] = rule<Float>("Sum[Float]") { _, values, _ ->
                values.sum()
            }
        }
    }
}
