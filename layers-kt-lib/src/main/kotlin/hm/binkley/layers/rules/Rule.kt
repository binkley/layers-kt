package hm.binkley.layers.rules

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.RuleSurface
import hm.binkley.layers.sets.FullnessFunction
import hm.binkley.layers.sets.LayerSet

abstract class Rule<in T, out R>(val name: String) : (RuleSurface) -> R {
    override fun toString(): String = "[Rule: $name]"

    companion object {
        fun <T> mostRecent(defaultValue: T): Rule<T, T> = MostRecentRule(defaultValue)
        fun sumAll(): Rule<Int, Int> = SumAllRule()
        fun floor(floor: Int): Rule<Int, Int> = FloorRule(floor)
        fun <L : Layer<L>> layerSet(fullness: FullnessFunction<L>) = LayerSet.rule(fullness)
    }
}
