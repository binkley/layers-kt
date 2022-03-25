package hm.binkley.layers.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.Rule

open class ConstantRule<K : Any, V : Any, T : V>(
    private val value: T,
    name: String = "Constant(value=$value)",
) : Rule<K, V, T>(name) {
    override fun invoke(
        key: K,
        values: Sequence<T>,
        layers: Layers<K, V, *>
    ): T = value
}
