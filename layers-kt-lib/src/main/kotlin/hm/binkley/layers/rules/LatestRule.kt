package hm.binkley.layers.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.Rule

open class LatestRule<K : Any, V : Any, T : V>(
    private val default: T,
    name: String = "Latest(default=$default)",
) : Rule<K, V, T>(name) {
    override fun invoke(
        key: K,
        values: Sequence<T>,
        layers: Layers<K, V, *>,
    ): T = values.lastOrNull() ?: default
}
