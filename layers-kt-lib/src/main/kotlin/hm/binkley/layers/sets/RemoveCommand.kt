package hm.binkley.layers.sets

import hm.binkley.layers.Layer

internal class RemoveCommand<L : Layer<L>>(private val layer: L) :
        LayerSetCommand<L>("Remove") {
    override fun invoke(set: LayerSet<L>) = set.remove(layer)
}
