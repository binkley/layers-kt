package hm.binkley.layers.domain.rules

import hm.binkley.layers.Rule

class StatBonusRule(
    key: String,
    private val dependsOn: String,
) : Rule<Int>(key) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        ((allValues[dependsOn] as Int) - 10) / 2

    override fun description() = "Bonus from $dependsOn"

    companion object {
        fun statBonusRule(key: String, dependsOn: String) =
            StatBonusRule(key, dependsOn)
    }
}
