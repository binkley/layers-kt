package hm.binkley.layers

sealed class Entry<T> {
    abstract override fun toString(): String
}

data class Value<T>(
    val value: T,
) : Entry<T>() {
    override fun toString() = "$value"
}

abstract class Rule<T> : Entry<T>() {
    abstract operator fun invoke(values: List<T>): T
    abstract fun description(): String

    final override fun toString() = "Rule: ${description()}"
}

val <T> T.asEntry: Entry<T> get() = Value(this)
