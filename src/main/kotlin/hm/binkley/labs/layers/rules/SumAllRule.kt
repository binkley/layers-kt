package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers

class SumAllRule : Rule<Int, Int>("Sum all") {
    override fun invoke(layers: Layers.RuleSurface) = layers.values<Int>().sum()
}
