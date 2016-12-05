package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface

internal class SumAllRule : Rule<Int>("Sum all") {
    override fun invoke(layers: RuleSurface) = layers.values<Int>().sum()
}
