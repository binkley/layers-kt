package hm.binkley.layers.rules

import hm.binkley.layers.Rule
import kotlin.Int.Companion.MIN_VALUE
import kotlin.math.max

class FloorRule(
    key: String,
    private val min: Int,
) : Rule<Int>(key) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        max(values.maxOrNull() ?: MIN_VALUE, min)

    override fun description() = "Floor(min=$min)"

    companion object {
        fun floorRule(key: String, min: Int) = FloorRule(key, min)
    }
}
