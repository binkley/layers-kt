package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.rpg.rules.statBonusRule
import hm.binkley.layers.rpg.rules.statRule

enum class BaseStat {
    MIGHT, DEFTNESS, GRIT, WIT, FORESIGHT, PRESENCE;

    val bonusKey: String get() = "$name-BONUS"
}

fun EditMap<String, Any>.addStatRules() = BaseStat.values().forEach {
    this[it.name] = statRule(it.name)
    this[it.bonusKey] = statBonusRule(it.name)
}
