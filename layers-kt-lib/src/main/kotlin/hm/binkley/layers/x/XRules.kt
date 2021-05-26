package hm.binkley.layers.x

interface XRules<K : Any, V : Any> {
    fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: () -> T,
    ): XRule<K, V, T>

    fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (List<T>) -> T,
    ): XRule<K, V, T>

    fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (List<T>, XLayersForRules<K, V, *>) -> T,
    ): XRule<K, V, T>

    fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (K, List<T>, XLayersForRules<K, V, *>) -> T,
    ): XRule<K, V, T>

    fun <T : V> constantRule(key: K, value: T): XRule<K, V, T>
    fun <T : V> latestOfRule(key: K, default: T): XRule<K, V, T>
}
