package hm.binkley.layers.x

/**
 * A layer rule for [key].  Rule invocation take 3 arguments:
 * - `key` requested (typically [key], but possibly another key if sharing a
 *   rule instance among keys)
 * - `values` for [key] in _newest to oldest_ order
 * - `layers` the layers container (simplest use: a map of keys to computed
 *   value)
 *
 * @todo Rethink rules knowing their keys -- overkill?
 * @todo Specialize layers? -- let rules call specialized methods?
 */
abstract class XRule<K : Any, V : Any, T : V>(
    val key: K,
    val name: String,
) : XValueOrRule<V>, (K, List<T>, XLayersForRules<K, V, *>) -> T {
    final override fun toString() = "<Rule/$key>: $name"
}
