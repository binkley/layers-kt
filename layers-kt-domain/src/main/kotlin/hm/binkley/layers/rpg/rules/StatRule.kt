package hm.binkley.layers.rpg.rules

import hm.binkley.layers.EditableMap
import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rpg.rules.StatRule.Companion.statRule
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule

class StatRule(
    stat: String,
) : NamedRule<Int>(stat, stat) {
    private val impl = latestOfRule(stat, 8)

    override fun invoke(values: List<Int>, allValues: ValueMap): Int =
        impl.invoke(values, allValues)

    companion object {
        fun statRule(stat: String) = StatRule(stat)
    }
}

class StatBonusRule(
    private val stat: String,
) : NamedRule<Int>("Stat-Bonus[Int](stat=$stat)", "$stat-BONUS") {
    override fun invoke(values: List<Int>, allValues: ValueMap) =
        ((allValues[stat] as Int) - 10) / 2

    companion object {
        fun statBonusRule(stat: String) = StatBonusRule(stat)
    }
}

fun EditableMap.installStat(stat: String) {
    this[stat] = statRule(stat)
    this["$stat-BONUS"] = statBonusRule(stat)
}
