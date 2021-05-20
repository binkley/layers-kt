package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class SumOfRule : NamedRule<Int>("Sum[Int]") {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        values.sum()

    companion object {
        private val INSTANCE = SumOfRule()

        fun sumOfRule() = INSTANCE
    }
}
