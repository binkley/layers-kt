package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers

open class RpgLayers private constructor(
    name: String,
) : DefaultMutableLayers<String, Any, RpgLayer>(
    name,
    initLayers = listOf(CharacterLayer(name)),
    defaultMutableLayer = { RpgLayer(it) }
) {
    companion object {
        fun newCharacter(name: String) = RpgLayers(name)
    }
}
