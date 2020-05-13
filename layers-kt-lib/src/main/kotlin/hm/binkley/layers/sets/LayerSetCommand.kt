package hm.binkley.layers.sets

import hm.binkley.layers.Layer

abstract class LayerSetCommand<L : Layer<L>>(val name: String) :
        (LayerSet<L>) -> Unit {
    override fun toString(): String = "[Command: $name]"

    companion object {
        fun <L : Layer<L>> add(layer: L): LayerSetCommand<L> =
            AddCommand(layer)

        fun <L : Layer<L>> remove(layer: L): LayerSetCommand<L> =
            RemoveCommand(layer)
    }
}
