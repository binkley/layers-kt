package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class ConstantRule<T>(
    key: String,
    private val value: T,
) : NamedRule<T>("Constant(constant=$value)", key) {
    override fun invoke(key: String, values: List<T>, allValues: ValueMap) =
        value

    companion object {
        fun <T> constantRule(key: String, constant: T) =
            ConstantRule(key, constant)

        fun <T> initConstantRule(key: String, constant: T) =
            key to constantRule(key, constant)
    }
}
