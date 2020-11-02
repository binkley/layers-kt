package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class SumOfRule(
    key: String,
    private val default: Int,
) : Rule<Int>(key) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        if (values.isEmpty()) default else values.sum()

    override fun description() = "Sum[Int]"

    companion object {
        fun sumOfRule(key: String, default: Int) = SumOfRule(key, default)
    }
}
