package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Rule
import hm.binkley.layers.ValueMap

class StatBonusRule(
    private val stat: String,
) : Rule<Int>("$stat-BONUS") {
    override fun invoke(values: List<Int>, allValues: ValueMap) =
        ((allValues[stat] as Int) - 10) / 2

    override fun description() = "Stat-Bonus(stat=$stat)"

    companion object {
        fun statBonusRule(stat: String) = StatBonusRule(stat)
    }
}
