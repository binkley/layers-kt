package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class ConstantRule<T>(
    key: String,
    private val value: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>, allValues: Map<String, Any>) = value
    override fun description() = "Constant(value=$value)"

    companion object {
        fun <T> constantRule(key: String, value: T) = ConstantRule(key, value)
    }
}
