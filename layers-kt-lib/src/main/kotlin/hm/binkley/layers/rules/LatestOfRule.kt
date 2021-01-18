package hm.binkley.layers.rules

import hm.binkley.layers.Rule
import hm.binkley.layers.defaultValue

open class LatestOfRule<T>(
    key: String,
    private val default: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>, allValues: Map<String, Any>) =
        values.firstOrNull() ?: default

    override fun description() = "Latest(default=${defaultValue()})"

    companion object {
        fun <T> latestOfRule(key: String, default: T) =
            LatestOfRule(key, default)
    }
}
