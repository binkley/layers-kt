package hm.binkley.layers.domain.rules

import hm.binkley.layers.Rule

class StatBonusRule(
    private val stat: String,
) : Rule<Int>("$stat-BONUS") {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        ((allValues[stat] as Int) - 10) / 2

    override fun description() = "Stat-Bonus(stat=$stat)"

    companion object {
        fun statBonusRule(stat: String) = StatBonusRule(stat)
    }
}
