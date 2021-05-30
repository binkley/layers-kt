package hm.binkley.layers.x

interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    fun <T : V> getOtherValueAs(key: K): T

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

open class ConstantRule<K : Any, V : Any, T : V>(
    private val value: T,
) : Rule<K, V, T>("Constant(value=$value)") {
    override fun invoke(key: K, values: List<T>, view: Map<K, V>): T = value
}

open class LatestRule<K : Any, V : Any, T : V>(
    private val default: T,
) : Rule<K, V, T>("Latest(default=$default)") {
    override fun invoke(key: K, values: List<T>, view: Map<K, V>): T =
        values.lastOrNull() ?: default
}
