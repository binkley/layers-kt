package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.util.stackOf

open class RpgLayers private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, RpgLayer>(
    name,
    initLayers = stackOf(CharacterLayer(), StatLayer()),
    defaultMutableLayer = { RpgLayer(it) }
) {
    companion object {
        fun newCharacter() = RpgLayers("IGNORED")
    }
}
