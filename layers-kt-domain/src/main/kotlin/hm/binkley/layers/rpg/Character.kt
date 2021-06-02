package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.util.stackOf

open class Character private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, RpgLayer>(
    name,
    initLayers = stackOf(PlayerLayer(), StatLayer()),
    defaultMutableLayer = { RpgLayer(it) }
) {
    companion object {
        fun newCharacter(name: String) = Character(name)
    }
}
