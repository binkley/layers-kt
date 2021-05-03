package hm.binkley.layers.rules

import hm.binkley.layers.Rule
import hm.binkley.layers.ValueMap
import hm.binkley.layers.defaultValue

open class LatestOfRule<T>(
    private val key: String,
    private val default: T,
) : Rule<T>() {
    override fun invoke(key: String, values: List<T>, allValues: ValueMap) =
        values.firstOrNull() ?: default

    override fun description() = "Latest(default=${defaultValue(key)})"

    companion object {
        fun <T> latestOfRule(key: String, default: T) =
            LatestOfRule(key, default)
    }
}
