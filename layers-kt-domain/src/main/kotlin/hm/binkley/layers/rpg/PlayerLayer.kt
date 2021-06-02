package hm.binkley.layers.rpg

class PlayerLayer : RpgLayer("Character") {
    init {
        edit {
            this["PLAYER-NAME"] = latestRule("")
            this["CHARACTER-NAME"] = latestRule("")
        }
    }
}
