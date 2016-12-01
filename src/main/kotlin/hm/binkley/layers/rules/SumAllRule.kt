package hm.binkley.layers.rules

import hm.binkley.layers.Layers

class SumAllRule : Rule<Int, Int>("Sum all") {
    override fun invoke(layers: Layers.RuleSurface) = layers.values<Int>().sum()
}
