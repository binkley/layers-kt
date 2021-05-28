package hm.binkley.layers.x

sealed interface ValueOrRule<V : Any>

data class Value<V : Any>(val value: V) : ValueOrRule<V> {
    override fun toString() = "<Value>: $value"
}

fun <T : Any> T.toValue() = Value(this)

abstract class Rule<K : Any, V : Any, T : V>(
    val name: String,
) : ValueOrRule<V>, (K, List<T>, Map<K, V>) -> T {
    override fun toString() = "<Rule>: $name"
}

interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    fun <T : V> getOtherValueAs(key: K): T

    fun <T : V> rule(
        name: String,
        block: (K, List<T>, Map<K, V>) -> T,
    ): Rule<K, V, T> = object : Rule<K, V, T>(name) {
        override fun invoke(
            key: K,
            values: List<T>,
            view: Map<K, V>,
        ): T = block(key, values, view)
    }

    fun <T : V> constantRule(value: T): Rule<K, V, T> =
        rule("Constant(value=$value)") { _, _, _ -> value }

    fun <T : V> latestOfRule(default: T): Rule<K, V, T> =
        rule("Latest(default=$default)") { _, values, _ ->
            values.lastOrNull() ?: default
        }
}