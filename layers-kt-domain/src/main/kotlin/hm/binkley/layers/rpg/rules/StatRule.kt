package hm.binkley.layers.rpg.rules

import hm.binkley.layers.EditMap
import hm.binkley.layers.Rule

/**
 * Stats are cumulative, not absolute.  To fix a stat at a value, add a new
 * constant rule.
 */
class StatRule(stat: String) :
    Rule<String, Any, Int>("Stat[Int](stat=$stat)") {
    override fun invoke(
        key: String,
        values: List<Int>,
        view: Map<String, Any>,
    ): Int = values.fold(8) { a, b -> a + b }
}

@Suppress("unused")
fun EditMap<String, Any>.statRule(stat: String) = StatRule(stat)

class StatBonusRule(private val stat: String) :
    Rule<String, Any, Int>("Stat-Bonus[Int](stat=$stat)") {
    override fun invoke(
        key: String,
        values: List<Int>,
        view: Map<String, Any>,
    ): Int = (view[stat] as Int - 10) / 2
}

@Suppress("unused")
fun EditMap<String, Any>.statBonusRule(stat: String) = StatBonusRule(stat)
