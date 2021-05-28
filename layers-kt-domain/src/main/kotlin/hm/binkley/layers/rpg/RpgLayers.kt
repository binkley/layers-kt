package hm.binkley.layers.rpg

import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.BaseStat.Companion.addStats
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule

fun newCharacter() = Layers.new(listOf(CharacterLayer("<INIT>"))).edit {
    this["PLAYER-NAME"] = latestOfRule("PLAYER-NAME", "")
    this["CHARACTER-NAME"] = latestOfRule("CHARACTER-NAME", "")

    addStats()
}

class CharacterLayer(name: String) : RpgLayer<CharacterLayer>(name)
