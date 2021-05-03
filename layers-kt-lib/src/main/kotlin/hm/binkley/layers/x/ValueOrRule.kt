package hm.binkley.layers.x

/** @todo Replace with [hm.binkley.layers.ValueOrRule] once typing is sorted out */
sealed class ValueOrRule<V> {
    abstract override fun toString(): String
}

/** @todo Replace with [hm.binkley.layers.Value] once typing is sorted out */
data class Value<V>(val value: V) : ValueOrRule<V>() {
    override fun toString() = "<Value>: $value"
}

/** @todo Replace with [hm.binkley.layers.Rule] once typing is sorted out */
abstract class Rule<K, V> :
    ValueOrRule<V>(), (List<V>, MutableMapList<K, V>) -> V {
    abstract fun description(): String

    final override fun toString() = "<Rule>: ${description()}"
}
