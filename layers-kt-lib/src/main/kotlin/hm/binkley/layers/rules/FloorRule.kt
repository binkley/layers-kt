package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface
import kotlin.math.max

internal class FloorRule(private val floor: Int) :
    Rule<Int>("Floor ($floor)") {
    override fun invoke(layers: RuleSurface): Int =
        max(layers.without(), floor)
}
