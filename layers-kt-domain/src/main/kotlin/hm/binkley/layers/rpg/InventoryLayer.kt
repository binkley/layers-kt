package hm.binkley.layers.rpg

class InventoryLayer : RpgLayer<InventoryLayer>("Inventory") {
    init {
        edit {
            this["ITEM-WEIGHT"] =
                rule<Float>("Sum[Float]") { _, values, _ ->
                    values.sum()
                }
        }
    }
}
