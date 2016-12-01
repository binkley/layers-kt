package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers
import java.lang.Math.max

class FloorRule(val floor: Int) : Rule<Int, Int>("Floor ($floor)") {
    override fun invoke(layers: Layers.RuleSurface): Int = max(layers.without(), floor)
}
