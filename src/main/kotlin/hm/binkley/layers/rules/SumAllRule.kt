package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface

class SumAllRule : Rule<Int, Int>("Sum all") {
    override fun invoke(layers: RuleSurface) = layers.values<Int>().sum()
}
