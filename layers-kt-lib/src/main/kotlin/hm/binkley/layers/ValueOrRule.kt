package hm.binkley.layers

/** Union type for values and rules. */
sealed class ValueOrRule<V : Any> {
    abstract override fun toString(): String
}

/** Wrapper for values. */
data class Value<V : Any>(
    /** The wrapped value. */
    val value: V,
) : ValueOrRule<V>() {
    override fun toString(): String = "<Value>$value"
}

/** Wraps [this] as a [Value]. */
fun <T : Any> T.toValue(): Value<T> = Value(this)

/** Base type for rules. */
abstract class Rule<K : Any, V : Any, T : V>(
    /** The name of the rule. */
    val name: String,
) : ValueOrRule<V>(), (K, Sequence<T>, Layers<K, V, *>) -> T {
    override fun toString(): String = "<Rule>$name"
}
