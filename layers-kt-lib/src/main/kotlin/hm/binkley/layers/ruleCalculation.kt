package hm.binkley.layers

internal sealed class RuleCalculation<T>(
    protected val values: MutableList<T>,
    protected val allValues: Map<String, Any>,
) {
    abstract fun add(entry: Entry<T>): RuleCalculation<T>
    abstract fun calculate(): T

    companion object {
        fun <T> lookingForRule(
            key: String,
            allValues: Map<String, Any>,
        ): RuleCalculation<T> =
            LookingForRule(key, mutableListOf(), allValues)
    }
}

private class LookingForRule<T>(
    private val key: String,
    values: MutableList<T>,
    allValues: Map<String, Any>,
) : RuleCalculation<T>(values, allValues) {
    override fun add(entry: Entry<T>) =
        when (entry) {
            is Rule -> HasRule(entry, values, allValues) // Switch modes
            is Value -> {
                values.add(entry.value)
                this
            }
        }

    override fun calculate() =
        throw Bug("Missing override: no rule for key: $key")
}

private class HasRule<T>(
    private val rule: Rule<T>,
    values: MutableList<T>,
    allValues: Map<String, Any>,
) : RuleCalculation<T>(values, allValues) {
    override fun add(entry: Entry<T>): RuleCalculation<T> = when (entry) {
        is Rule -> this // First rule found wins; ignore the rest
        is Value -> {
            values.add(entry.value)
            this
        }
    }

    override fun calculate() = rule(values, allValues)
}
