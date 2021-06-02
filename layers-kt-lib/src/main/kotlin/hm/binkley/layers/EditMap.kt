package hm.binkley.layers

interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    fun <T : V> rule(
        name: String,
        block: (K, List<T>, Map<K, V>) -> T,
    ): Rule<K, V, T> = object : Rule<K, V, T>(name) {
        override fun invoke(key: K, values: List<T>, view: Map<K, V>): T =
            block(key, values, view)
    }

    fun <T : V> constantRule(value: T) = ConstantRule<K, V, T>(value)
    fun <T : V> latestRule(default: T) = LatestRule<K, V, T>(default)
}

interface LayersEditMap<K : Any, V : Any> : EditMap<K, V> {
    /**
     * Returns the value of another [key] as computed by layers.  The value
     * of the current key used in a rule context is `null` (this avoids
     * recursion and stack overflow).
     */
    fun <T : V> getAs(key: K, except: Rule<K, V, T>? = null): T
}

open class ConstantRule<K : Any, V : Any, T : V>(
    private val value: T,
    name: String = "Constant(value=$value)",
) : Rule<K, V, T>(name) {
    override fun invoke(key: K, values: List<T>, view: Map<K, V>): T = value
}

open class LatestRule<K : Any, V : Any, T : V>(
    private val default: T,
    name: String = "Latest(default=$default)",
) : Rule<K, V, T>(name) {
    override fun invoke(key: K, values: List<T>, view: Map<K, V>): T =
        values.lastOrNull() ?: default
}
