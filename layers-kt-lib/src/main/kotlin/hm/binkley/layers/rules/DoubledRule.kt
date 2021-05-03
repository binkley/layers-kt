package hm.binkley.layers.rules

import hm.binkley.layers.ValueMap
import hm.binkley.layers.defaultValue

class DoubledRule(
    private val key: String,
    default: Int,
) : LatestOfRule<Int>(key, default) {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        2 * super.invoke(key, values, allValues)

    override fun description() = "Doubled[Int](default=${defaultValue(key)})"

    companion object {
        fun doubledRule(key: String, default: Int) =
            DoubledRule(key, default)
    }
}
