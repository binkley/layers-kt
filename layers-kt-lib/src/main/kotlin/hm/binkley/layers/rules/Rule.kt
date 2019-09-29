package hm.binkley.layers.rules

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.RuleSurface
import hm.binkley.layers.sets.FullnessFunction
import hm.binkley.layers.sets.LayerSet.Companion.rule
import javax.annotation.processing.Generated

abstract class Rule<out R>(val name: String) : (RuleSurface) -> R {
    @Generated // Lie to JaCoCo
    override fun toString(): String = "[Rule: $name]"

    companion object {
        fun <T> mostRecent(defaultValue: T): Rule<T> = MostRecentRule(defaultValue)
        fun sumAll(): Rule<Int> = SumAllRule()
        fun floor(floor: Int): Rule<Int> = FloorRule(floor)
        fun <L : Layer<L>> layerSet(fullness: FullnessFunction<L>) = rule(fullness)
    }
}
