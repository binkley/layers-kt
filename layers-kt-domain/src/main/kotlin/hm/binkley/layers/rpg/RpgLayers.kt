package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.rpg.BaseStat.Companion.addStatRules

open class RpgLayers private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, RpgLayer>(
    name,
    initLayers = listOf(
        CharacterLayer(name).edit {
            this["PLAYER-NAME"] = latestRule("")
            this["CHARACTER-NAME"] = latestRule("")
            addStatRules()
        }
    ),
    defaultMutableLayer = { RpgLayer(it) }
) {
    companion object {
        fun newCharacter(name: String) = RpgLayers(name)
    }
}

class CharacterLayer(name: String) : RpgLayer(name)
