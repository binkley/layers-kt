package hm.binkley.layers.x.rules

import hm.binkley.layers.x.XLayersForRules
import hm.binkley.layers.x.XRule

/** @todo Single rule for summing Number types (Int, Float, et al) */
class XSumOfRule<K : Any>(
    key: K,
) : XRule<K, Any, Int>(key, "Sum[Int]") {
    override fun invoke(
        key: K,
        values: List<Int>,
        layers: XLayersForRules<K, Any, *>,
    ) = values.sum()

    companion object {
        fun <K : Any> sumOfRule(key: K) = XSumOfRule(key)
    }
}
