package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.rpg.rules.statBonusRule
import hm.binkley.layers.rpg.rules.statRule

enum class BaseStat {
    MIGHT, DEFTNESS, GRIT, WIT, FORESIGHT, PRESENCE;

    companion object {
        fun EditMap<String, Any>.addStatRules() = values().forEach {
            this[it.name] = statRule(it.name)
            this["${it.name}-BONUS"] = statBonusRule(it.name)
        }
    }
}
