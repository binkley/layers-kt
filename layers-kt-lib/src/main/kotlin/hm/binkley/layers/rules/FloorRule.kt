package hm.binkley.layers.rules

import hm.binkley.layers.Rule
import kotlin.Int.Companion.MIN_VALUE
import kotlin.math.max

class FloorRule(
    key: String,
    private val floor: Int,
) : Rule<Int>(key) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        max(values.maxOrNull() ?: MIN_VALUE, floor)

    override fun description() = "Floor: $floor"

    companion object {
        fun floorRule(key: String, floor: Int) = FloorRule(key, floor)
    }
}
