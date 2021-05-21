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
abstract class Rule<V> : ValueOrRule<V>(), (String, List<V>, ValueMap) -> V {
    abstract fun description(): String

    final override fun toString() = "<Rule>: ${description()}"
}

/** A convenience class. */
abstract class NamedRule<V>(
    private val name: String,
) : Rule<V>() {
    final override fun description(): String = name
}

fun <V> ruleFor(
    block: (key: String, List<V>, ValueMap) -> V,
): NamedRule<V> = object : NamedRule<V>("<Anonymous>") {
    override fun invoke(key: String, values: List<V>, allValues: ValueMap) =
        block(key, values, allValues)
}
