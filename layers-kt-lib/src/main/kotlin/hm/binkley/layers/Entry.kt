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
 *
 * @todo Merge/encapsulate via `ruleCalculation.kt`?
 */
sealed class Entry<T> {
    abstract override fun toString(): String
}

/**
 * A direct value in an individual layer entry without regard to the key
 * used to lookup this value.
 */
data class Value<T>(
    val value: T,
) : Entry<T>() {
    override fun toString() = "<Value>: $value"
}

/**
 * A computed value for layers based on all direct values over each
 * individual layer. The computation may consider the "key" for the value
 * requested.
 */
abstract class Rule<T>(
    protected val key: String,
) : Entry<T>() {
    /**
     * Computes a value for [key] based on a "vertical" view of all [values]
     * from each layer for the key in order from latest to oldest, and a
     * "horizontal" view of [allValues] as currently computed for all keys.
     */
    abstract operator fun invoke(
        values: List<T>,
        allValues: Map<String, Any>,
    ): T

    abstract fun description(): String

    final override fun toString() = "<Rule>[$key]: ${description()}"
}

fun <T> Rule<T>.defaultValue() = this(emptyList(), emptyMap())
fun <T> T.toValue(): Entry<T> = Value(this)

fun <T> ruleFor(
    key: String,
    block: (List<T>, Map<String, Any>) -> T,
) = object : Rule<T>(key) {
    override fun invoke(
        values: List<T>,
        allValues: Map<String, Any>,
    ) = block(values, allValues)

    override fun description() = "<Anonymous>"
}
