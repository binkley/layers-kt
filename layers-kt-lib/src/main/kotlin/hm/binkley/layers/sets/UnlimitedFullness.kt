package hm.binkley.layers.sets

import hm.binkley.layers.Layer

internal class UnlimitedFullness<L : Layer<L>> :
        FullnessFunction<L>("Unlimited") {
    override fun invoke(set: LayerSet<L>, layer: L): Boolean = false
}
