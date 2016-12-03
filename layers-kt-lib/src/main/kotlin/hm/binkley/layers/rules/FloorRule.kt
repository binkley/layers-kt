package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface
import java.lang.Math.max

internal class FloorRule(val floor: Int) : Rule<Int, Int>("Floor ($floor)") {
    override fun invoke(layers: RuleSurface): Int = max(layers.without(), floor)
}
