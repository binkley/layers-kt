package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers

open class RpgLayers private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, RpgLayer>(
    name,
    initLayers = listOf(CharacterLayer(), StatLayer()),
    defaultMutableLayer = { RpgLayer(it) }
) {
    companion object {
        fun newCharacter() = RpgLayers("IGNORED")
    }
}
