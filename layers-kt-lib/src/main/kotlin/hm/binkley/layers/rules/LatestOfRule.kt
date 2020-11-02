package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class LatestOfRule<T>(
    key: String,
    private val default: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>, allValues: Map<String, Any>) =
        if (values.isEmpty()) default else values.first()

    override fun description() = "Latest"

    companion object {
        fun <T> latestOfRule(key: String, default: T) =
            LatestOfRule(key, default)
    }
}
