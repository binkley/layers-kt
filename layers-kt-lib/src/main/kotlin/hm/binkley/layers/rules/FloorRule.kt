package hm.binkley.layers.rules

import hm.binkley.layers.NamedRule
import hm.binkley.layers.ValueMap
import kotlin.Int.Companion.MIN_VALUE
import kotlin.math.max

class FloorRule(
    private val min: Int,
) : NamedRule<Int>("Floor[Int](min=$min)") {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        max(values.maxOrNull() ?: MIN_VALUE, min)

    companion object {
        fun floorRule(min: Int) = FloorRule(min)

        fun initFloorRule(key: String, min: Int) =
            key to floorRule(min)
    }
}
