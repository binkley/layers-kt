package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class SumOfRule(
    key: String,
    private val default: Int,
) : NamedRule<Int>("Sum[Int]", key) {
    override fun invoke(values: List<Int>, allValues: ValueMap) =
        if (values.isEmpty()) default else values.sum()

    companion object {
        fun sumOfRule(key: String, default: Int) = SumOfRule(key, default)

        fun initSumOfRule(key: String, default: Int) =
            key to sumOfRule(key, default)
    }
}
