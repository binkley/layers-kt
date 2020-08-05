package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface
import kotlin.math.max

internal class AtLeastRule(private val floor: Int) :
    Rule<Int>("At least ($floor)") {
    override fun invoke(layers: RuleSurface): Int =
        max(layers.without(), floor)
}
