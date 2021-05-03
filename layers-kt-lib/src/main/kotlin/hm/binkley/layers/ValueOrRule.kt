package hm.binkley.layers

/**
 * A layer entry is distinct from a
 * [`Map.Entry`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/-entry/)
 * or [`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/) in
 * that:
 * * "key" is not applicable to `Value` which violates the contract of
 *   `Map.Entry` and `Pair.first`
 * * "value" is not computed for `Rule` until provided an argument which
 *   violates the contract of `Map.Entry` and `Pair.second`
 *
 * @see Rule which computes "value" from arguments, and makes use of "key"
 * @see Value which has a fixed value for "value", and ignores "key
 */
sealed class ValueOrRule<V> {
    abstract override fun toString(): String
}

/**
 * A direct value in an individual layer entry without regard to the key
 * used to lookup this value.
 */
data class Value<V>(
    val value: V,
) : ValueOrRule<V>() {
    override fun toString() = "<Value>: $value"
}

/**
 * A computed value for layers based on all direct values over each
 * individual layer. The computation may consider the "key" for the value
 * requested.
 */
abstract class Rule<V>(
    val key: String,
) : ValueOrRule<V>() {
    /**
     * Computes a value for [key] based on a "vertical" view of all [values]
     * from each layer for the key in order from latest to oldest, and a
     * "horizontal" view of [allValues] as currently computed for all other
     * keys.
     */
    abstract operator fun invoke(
        key: String,
        values: List<V>,
        allValues: ValueMap,
    ): V

    abstract fun description(): String

    final override fun toString() = "<Rule>[$key]: ${description()}"
}

/** A convenience class. */
abstract class NamedRule<V>(
    private val name: String,
    key: String,
) : Rule<V>(key) {
    final override fun description(): String = name
}

/** @todo This only works for certain types of rule, and is generally bogus */
fun <V> Rule<V>.defaultValue() = this(key, emptyList(), emptyMap())
fun <V> V.toValue(): ValueOrRule<V> = Value(this)

fun <V> ruleFor(
    key: String,
    block: (key: String, List<V>, ValueMap) -> V,
) = object : NamedRule<V>("<Anonymous>", key) {
    override fun invoke(key: String, values: List<V>, allValues: ValueMap) =
        block(key, values, allValues)
}
