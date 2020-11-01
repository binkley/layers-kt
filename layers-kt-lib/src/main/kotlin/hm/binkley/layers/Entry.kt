package hm.binkley.layers

/**
 * A layer map entry, distinct from a
 * [`Map.Entry`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/-entry/)
 * or [`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/) in
 * that:
 * * The "key" is only sometimes applicable
 * * The "value" might not be computed until provided an argument
 *
 * Note that `Pair` is a data class, and requires fixed key/value arguments in
 * its constructor; `Map.Entry` is an interface, and more flexible in this
 * context.
 *
 * @see Rule which computes "value" from arguments, and makes use of "key"
 * @see Value which has a fixed value for "value", and ignores "key
 */
sealed class Entry<T> {
    abstract override fun toString(): String
}

data class Value<T>(
    val value: T,
) : Entry<T>() {
    override fun toString() = "Value: $value"
}

abstract class Rule<T>(
    protected val key: String,
) : Entry<T>() {
    abstract operator fun invoke(values: List<T>): T
    abstract fun description(): String

    final override fun toString() = "<Rule>[$key]: ${description()}"
}

fun <T> T.toEntry(): Entry<T> = Value(this)
