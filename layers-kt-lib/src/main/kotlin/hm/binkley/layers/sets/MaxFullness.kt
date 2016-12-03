package hm.binkley.layers.sets

import hm.binkley.layers.Layer

internal class MaxFullness<L : Layer<L>>(private val max: Int) : FullnessFunction<L>("Max ($max)") {
    override fun invoke(set: LayerSet<L>, layer: L): Boolean = (max == set.size)
}
