package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.rules.statBonusRule
import hm.binkley.layers.rpg.rules.statRule

/** Character stats. */
enum class Stat {
    /** Raw physical power. */
    MIGHT,
    /** Skill and grace. */
    DEFTNESS,
    /** Toughness, stamina, and health. */
    GRIT,
    /** Cleverness, memory, and mental aptitude. */
    WIT,
    /** Judgement and common sense. */
    FORESIGHT,
    /** Impact on others. */
    PRESENCE;

    /** The map key for the modifier of this stat. */
    val bonusKey: String get() = "$name-BONUS"
}

/** Adds stat and modifier rules. */
fun RpgEditMap.addStatRules(): Unit = Stat.values().forEach {
    this[it.name] = statRule(it.name)
    this[it.bonusKey] = statBonusRule(it.name)
}
