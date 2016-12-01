package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers

abstract class Rule<in T, out R>(val name: String) : (Layers.RuleSurface) -> R {
    override fun toString(): String = "[Rule: $name]"

    companion object {
        fun <T> mostRecent(defaultValue: T): Rule<T, T> = MostRecentRule(defaultValue)
        fun sumAll(): Rule<Int, Int> = SumAllRule()
        fun floor(floor: Int): Rule<Int, Int> = FloorRule(floor)
    }
}
