package hm.binkley.layers

sealed class ValueOrRule<V : Any> {
    abstract override fun toString(): String
}

data class Value<V : Any>(val value: V) : ValueOrRule<V>() {
    override fun toString() = "<Value>$value"
}

fun <T : Any> T.toValue() = Value(this)

abstract class Rule<K : Any, V : Any, T : V>(
    val name: String,
) : ValueOrRule<V>(), (K, List<T>, Layers<K, V, *>) -> T {
    override fun toString() = "<Rule>$name"
}
