package hm.binkley.layers

sealed class ValueOrRule<V : Any> {
    abstract override fun toString(): String
}

data class Value<V : Any>(val value: V) : ValueOrRule<V>() {
    override fun toString(): String = "<Value>$value"
}

fun <T : Any> T.toValue(): Value<T> = Value(this)

abstract class Rule<K : Any, V : Any, T : V>(
    val name: String,
) : ValueOrRule<V>(), (K, Sequence<T>, Layers<K, V, *>) -> T {
    override fun toString(): String = "<Rule>$name"
}
