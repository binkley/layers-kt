package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers

abstract class Rule<in T, out R>(val name: String) : (Layers.RuleSurface) -> R {
    override fun toString(): String = "[Rule: $name]"

    companion object {
        fun sumAll(): Rule<Int, Int> = SumAllRule()
    }
}
