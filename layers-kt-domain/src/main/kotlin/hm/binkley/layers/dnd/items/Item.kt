package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.LayerSurface

abstract class Item<L : Item<L>>(layers: LayerSurface, name: String,
        val weight: Weight, val volume: Volume)
    : Layer<L>(layers, name) {
    init {
        put(Weight::class, weight) // TODO: Think about this
    }
}
