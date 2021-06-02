package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.rules.statBonusRule
import hm.binkley.layers.rpg.rules.statRule

enum class Stat {
    MIGHT, DEFTNESS, GRIT, WIT, FORESIGHT, PRESENCE;

    val bonusKey: String get() = "$name-BONUS"
}

fun RpgEditMap.addStatRules() = Stat.values().forEach {
    this[it.name] = statRule(it.name)
    this[it.bonusKey] = statBonusRule(it.name)
}
