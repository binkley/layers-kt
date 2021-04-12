package hm.binkley.layers.rules

import hm.binkley.layers.Rule
import hm.binkley.layers.ValueMap

class ConstantRule<T>(
    key: String,
    private val value: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>, allValues: ValueMap) = value
    override fun description() = "Constant(constant=$value)"

    companion object {
        fun <T> constantRule(key: String, constant: T) =
            ConstantRule(key, constant)

        fun <T> initConstantRule(key: String, constant: T) =
            key to constantRule(key, constant)
    }
}
