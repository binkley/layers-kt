package hm.binkley.layers.rpg

import hm.binkley.layers.Layers
import hm.binkley.layers.MutablePlainLayer
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule

class Rpg(
    name: String,
) : MutablePlainLayer(name) {
    companion object {
        fun newCharacter() = Layers.new(listOf(Rpg("<INIT>"))).edit {
            this["PLAYER-NAME"] = latestOfRule("PLAYER-NAME", "")
            this["CHARACTER-NAME"] = latestOfRule("CHARACTER-NAME", "")
            this["MIGHT"] = latestOfRule("MIGHT", 8)
            this["MIGHT-BONUS"] = statBonusRule("MIGHT")
        }
    }
}
