package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class SumRule(
    key: String,
    private val default: Int,
) : Rule<Int>(key) {
    override fun invoke(values: List<Int>) =
        if (values.isEmpty()) default else values.sum()

    override fun description() = "Sum[Int]"

    companion object {
        fun sumOfRule(key: String, default: Int) = SumRule(key, default)
    }
}
