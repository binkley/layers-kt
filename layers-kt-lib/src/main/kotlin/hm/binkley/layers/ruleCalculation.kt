package hm.binkley.layers

internal sealed class RuleCalculation<T> {
    abstract fun add(entry: Entry<T>): RuleCalculation<T>
    abstract fun calculate(): T

    companion object {
        fun <T> lookingForRule(key: String): RuleCalculation<T> =
            LookingForRule(key, mutableListOf())
    }
}

private class LookingForRule<T>(
    private val key: String,
    private val values: MutableList<T>,
) : RuleCalculation<T>() {
    override fun add(entry: Entry<T>) =
        when (entry) {
            is Rule -> HasRule(entry, values) // Switch modes
            is Value -> {
                values.add(entry.value)
                this
            }
        }

    override fun calculate() =
        throw IllegalStateException("No rule for key: $key")
}

private class HasRule<T>(
    private val rule: Rule<T>,
    private val values: MutableList<T>,
) : RuleCalculation<T>() {
    override fun add(entry: Entry<T>): RuleCalculation<T> = when (entry) {
        is Rule -> this // First rule found wins; ignore the rest
        is Value -> {
            values.add(entry.value)
            this
        }
    }

    override fun calculate() = rule(values)
}
