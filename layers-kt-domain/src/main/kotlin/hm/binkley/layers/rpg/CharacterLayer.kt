package hm.binkley.layers.rpg

class CharacterLayer : RpgLayer("Character") {
    init {
        edit {
            this["PLAYER-NAME"] = latestRule("")
            this["CHARACTER-NAME"] = latestRule("")
        }
    }
}
