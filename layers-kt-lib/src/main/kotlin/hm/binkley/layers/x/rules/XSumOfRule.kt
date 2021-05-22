package hm.binkley.layers.x.rules

import hm.binkley.layers.x.XLayers
import hm.binkley.layers.x.XRule

/** @todo Single rule for summing Number types (Int, Float, et al) */
class XSumOfRule : XRule<Any, Int>("Sum") {
    override fun invoke(
        values: List<Int>,
        layers: XLayers<*, Any, *>,
    ) = values.sum()

    companion object {
        private val INSTANCE = XSumOfRule()
        fun sumOfRule() = INSTANCE
    }
}
