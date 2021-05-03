package hm.binkley.layers.rules

import hm.binkley.layers.ValueMap

class DoubledRule(
    key: String,
    private val default: Int,
) : LatestOfRule<Int>(key, default) {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        2 * super.invoke(key, values, allValues)

    override fun description() = "Doubled[Int](default=${2 * default})"

    companion object {
        fun doubledRule(key: String, default: Int) =
            DoubledRule(key, default)
    }
}
