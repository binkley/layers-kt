package hm.binkley.layers.rpg.rules

import hm.binkley.layers.x.EditMap
import hm.binkley.layers.x.LatestRule
import hm.binkley.layers.x.Rule

class StatRule(stat: String) :
    LatestRule<String, Any, Int>(8, "Stat[Int](stat=$stat)")

fun EditMap<String, Any>.statRule(stat: String) = StatRule(stat)

class StatBonusRule(private val stat: String) :
    Rule<String, Any, Int>("Stat-Bonus[Int](stat=$stat)") {
    override fun invoke(
        key: String,
        values: List<Int>,
        view: Map<String, Any>,
    ): Int = (view[stat] as Int - 10) / 2
}

fun EditMap<String, Any>.statBonusRule(stat: String) = StatBonusRule(stat)
