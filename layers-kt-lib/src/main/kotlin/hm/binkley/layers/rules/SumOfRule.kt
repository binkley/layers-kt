package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class SumOfRule(
    private val default: Int,
) : NamedRule<Int>("Sum[Int]") {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        if (values.isEmpty()) default else values.sum()

    companion object {
        fun sumOfRule(default: Int) = SumOfRule(default)
    }
}
