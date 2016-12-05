package hm.binkley.layers.sets

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.RuleSurface
import hm.binkley.layers.rules.Rule

internal class LayerSetRule<L : Layer<L>>(private val fullness: FullnessFunction<L>)
    : Rule<LayerSet<L>>("Set") {
    override fun invoke(layers: RuleSurface): LayerSet<L> {
        val set = LayerSet(fullness)
        layers.values<LayerSetCommand<L>>().
                forEach { it.invoke(set) }
        return set
    }
}
