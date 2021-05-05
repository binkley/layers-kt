package hm.binkley.layers.x

sealed interface ValueOrRule<V> {
    abstract override fun toString(): String
}

data class Value<V>(
    val value: V,
) : ValueOrRule<V> {
    override fun toString() = "<Value>: $value"
}

fun <V> V.toValue() = Value(this)

abstract class Rule<K, MapV, RuleV : MapV> :
    ValueOrRule<MapV>, (K, List<RuleV>, Layers<K, MapV>) -> RuleV {
    abstract fun description(): String

    override fun toString() = "<Rule>: ${description()}"
}

fun <K, MapV, RuleV : MapV> namedRule(
    name: String,
    block: (K, List<RuleV>, Layers<K, MapV>) -> RuleV,
): Rule<K, MapV, RuleV> {
    return object : Rule<K, MapV, RuleV>() {
        override fun description() = name

        override fun invoke(
            key: K,
            values: List<RuleV>,
            layers: Layers<K, MapV>,
        ): RuleV = block(key, values, layers)
    }
}
