package hm.binkley.layers.rpg.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class StatBonusRule(
    private val stat: String,
) : NamedRule<Int>("Stat-Bonus[Int](stat=$stat)", "$stat-BONUS") {
    override fun invoke(values: List<Int>, allValues: ValueMap) =
        ((allValues[stat] as Int) - 10) / 2

    companion object {
        fun statBonusRule(stat: String) = StatBonusRule(stat)
    }
}
