package hm.binkley.layers.rpg

import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule

fun newCharacter() = Layers.new(listOf(RpgLayer("<INIT>"))).edit {
    this["PLAYER-NAME"] = latestOfRule("PLAYER-NAME", "")
    this["CHARACTER-NAME"] = latestOfRule("CHARACTER-NAME", "")
    this["MIGHT"] = latestOfRule("MIGHT", 8)
    this["MIGHT-BONUS"] = statBonusRule("MIGHT")
}
