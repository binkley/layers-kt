package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap

class ConstantRule<T>(
    private val value: T,
) : NamedRule<T>("Constant(constant=$value)") {
    override fun invoke(key: String, values: List<T>, allValues: ValueMap) =
        value

    companion object {
        fun <T> constantRule(constant: T) = ConstantRule(constant)
    }
}
