package hm.binkley.layers.rpg

class CharacterLayer(name: String) : RpgLayer(name) {
    init {
        edit {
            this["PLAYER-NAME"] = latestRule("")
            this["CHARACTER-NAME"] = latestRule("")
            addStatRules()
        }
    }
}
