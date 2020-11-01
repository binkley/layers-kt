package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class LatestRule<T>(
    key: String,
    private val default: T,
) : Rule<T>(key) {
    override fun invoke(values: List<T>) =
        if (values.isEmpty()) default else values.first()

    override fun description() = "Latest"

    companion object {
        fun <T> latestOfRule(key: String, default: T) =
            LatestRule(key, default)
    }
}
