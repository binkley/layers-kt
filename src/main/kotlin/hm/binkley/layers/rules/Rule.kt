package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface

abstract class Rule<in T, out R>(val name: String) : (RuleSurface) -> R {
    override fun toString(): String = "[Rule: $name]"

    companion object {
        fun <T> mostRecent(defaultValue: T): Rule<T, T> = MostRecentRule(defaultValue)
        fun sumAll(): Rule<Int, Int> = SumAllRule()
        fun floor(floor: Int): Rule<Int, Int> = FloorRule(floor)
    }
}
