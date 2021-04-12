package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue

class DoubledRule(
    key: String,
    default: Int,
) : LatestOfRule<Int>(key, default) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        2 * super.invoke(values, allValues)

    override fun description() = "Doubled(default=${defaultValue()})"

    companion object {
        fun doubledRule(key: String, default: Int) =
            DoubledRule(key, default)

        fun initDoubledRule(key: String, default: Int) =
            key to doubledRule(key, default)
    }
}
