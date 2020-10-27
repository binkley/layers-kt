package hm.binkley.layers.rules

import hm.binkley.layers.Rule

class LatestRule<T> : Rule<T>() {
    override fun invoke(values: List<T>) = values.first()
    override fun description() = "Latest"

    companion object {
        fun <T> latestOf(values: List<T>) = LatestRule<T>()(values)
    }
}
