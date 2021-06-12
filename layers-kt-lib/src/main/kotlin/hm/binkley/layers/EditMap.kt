package hm.binkley.layers

import hm.binkley.layers.rules.ConstantRule
import hm.binkley.layers.rules.LatestRule

interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    fun <T : V> rule(
        name: String,
        block: (K, List<T>, Layers<K, V, *>) -> T,
    ): Rule<K, V, T> = object : Rule<K, V, T>(name) {
        override fun invoke(
            key: K,
            values: List<T>,
            layers: Layers<K, V, *>,
        ): T = block(key, values, layers)
    }

    fun <T : V> constantRule(value: T) = ConstantRule<K, V, T>(value)
    fun <T : V> latestRule(default: T) = LatestRule<K, V, T>(default)
}
