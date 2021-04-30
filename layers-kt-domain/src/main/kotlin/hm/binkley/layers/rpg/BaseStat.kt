package hm.binkley.layers.rpg

import hm.binkley.layers.EditableMap
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rpg.rules.StatRule.Companion.statRule

enum class BaseStat {
    MIGHT, DEFTNESS, GRIT, WIT, FORESIGHT, PRESENCE;

    private fun installInto(layer: EditableMap) {
        layer[name] = statRule(name)
        layer["$name-BONUS"] = statBonusRule(name)
    }

    companion object {
        fun EditableMap.installStats() = values().forEach {
            it.installInto(this)
        }
    }
}
