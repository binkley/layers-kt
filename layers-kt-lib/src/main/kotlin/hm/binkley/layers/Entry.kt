package hm.binkley.layers

/** @todo Unify with Entry and/or Pair */
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
