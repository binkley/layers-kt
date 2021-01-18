package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class ConstantRule<T>(
    key: String,
    private val constant: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>, allValues: Map<String, Any>) =
        constant

    override fun description() = "Constant: $constant"

    companion object {
        fun <T> constantRule(key: String, constant: T) =
            ConstantRule(key, constant)
    }
}
