package hm.binkley.layers.rules

import hm.binkley.layers.Rule

open class LatestRule<K : Any, V : Any, T : V>(
    private val default: T,
    name: String = "Latest(default=$default)",
) : Rule<K, V, T>(name) {
    override fun invoke(key: K, values: List<T>, view: Map<K, V>): T =
        values.lastOrNull() ?: default
}
