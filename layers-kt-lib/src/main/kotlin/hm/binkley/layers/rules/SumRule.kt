package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class SumRule : Rule<Int>() {
    override fun invoke(values: List<Int>) = values.sum()
    override fun description() = "Sum"

    companion object {
        fun sumOf(values: List<Int>): Int {
            return if (values.isEmpty()) 0 else SumRule()(values)
        }
    }
}
