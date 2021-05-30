package hm.binkley.layers.rpg

import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.BaseStat.Companion.addStats
import hm.binkley.layers.rules.LatestRule.Companion.latestRule

fun newCharacter() = Layers.new(listOf(CharacterLayer("<INIT>"))).edit {
    this["PLAYER-NAME"] = latestRule("PLAYER-NAME", "")
    this["CHARACTER-NAME"] = latestRule("CHARACTER-NAME", "")

    addStats()
}

class CharacterLayer(name: String) : RpgLayer<CharacterLayer>(name)
