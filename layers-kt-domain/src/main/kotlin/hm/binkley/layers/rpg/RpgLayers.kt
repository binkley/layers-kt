package hm.binkley.layers.rpg

import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.rules.installStat
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule

fun newCharacter() = Layers.new(listOf(RpgLayer("<INIT>"))).edit {
    this["PLAYER-NAME"] = latestOfRule("PLAYER-NAME", "")
    this["CHARACTER-NAME"] = latestOfRule("CHARACTER-NAME", "")

    installStat("MIGHT")
    installStat("DEFTNESS")
    installStat("GRIT")
    installStat("WIT")
    installStat("FORESIGHT")
    installStat("PRESENCE")
}
