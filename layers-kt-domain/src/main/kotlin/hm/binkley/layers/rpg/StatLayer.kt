package hm.binkley.layers.rpg

/**
 * This layer tracks character stat scores and modifiers.
 * See [Stat] for supported stats.
 * Supported keys are:
 * - "<STAT>" &mdash; the enum name from [Stat]
 * - "<STAT>-BONUS" &mdash; the modifier for the stat
 */
class StatLayer : RpgLayer<StatLayer>("Stats") {
    init {
        edit { addStatRules() }
    }
}
