package hm.binkley.layers.sets

import hm.binkley.layers.Layer

internal class AddCommand<L : Layer<L>>(private val layer: L) : LayerSetCommand<L>("Add") {
    override fun invoke(set: LayerSet<L>) = set.add(layer)
}
