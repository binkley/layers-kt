package hm.binkley.layers.rpg

class PlayerLayer : RpgLayer<PlayerLayer>("Character") {
    init {
        edit {
            this["PLAYER_NAME"] = latestRule("")
            this["CHARACTER_NAME"] = latestRule("")
        }
    }
}
