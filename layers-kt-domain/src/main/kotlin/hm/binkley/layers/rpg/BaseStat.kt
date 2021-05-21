package hm.binkley.layers.rpg

import hm.binkley.layers.LayerMutableMap
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rpg.rules.StatRule.Companion.statRule

enum class BaseStat {
    MIGHT, DEFTNESS, GRIT, WIT, FORESIGHT, PRESENCE;

    private fun installInto(layer: LayerMutableMap) {
        layer[name] = statRule(name)
        layer["$name-BONUS"] = statBonusRule(name)
    }

    companion object {
        fun LayerMutableMap.installStats() = values().forEach {
            it.installInto(this)
        }
    }
}
