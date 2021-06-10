package hm.binkley.layers.rpg

class PlayerLayer : RpgLayer<PlayerLayer>("Character") {
    init {
        edit {
            this["PLAYER-NAME"] = latestRule("")
            this["CHARACTER-NAME"] = latestRule("")
            this["ITEM-WEIGHT"] =
                rule<Float>("Sum[Float]") { _, values, _ ->
                    values.sum()
                }
        }
    }
}
