package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.LayerSurface
import hm.binkley.layers.sets.LayerSetCommand.Companion.add
import hm.binkley.layers.sets.LayerSetCommand.Companion.remove

sealed class Attunement(layers: LayerSurface, name: String)
    : Layer<Attunement>(layers, name) {
    private class Attune<L : AttunementItem<L>>(layers: LayerSurface, item: L)
        : Attunement(layers, "Attune ${item.name}") {
        init {
            put(Attunement::class, add(item))
        }
    }

    private class Detune<L : AttunementItem<L>>(layers: LayerSurface, item: L)
        : Attunement(layers, "Detune ${item.name}") {
        init {
            put(Attunement::class, remove(item))
        }
    }

    companion object {
        fun <L : AttunementItem<L>> attune(item: L)
                : (LayerSurface) -> Attunement {
            fun apply(layers: LayerSurface): Attunement = Attune(layers, item)
            return ::apply
        }

        fun <L : AttunementItem<L>> detune(item: L)
                : (LayerSurface) -> Attunement {
            fun apply(layers: LayerSurface): Attunement = Detune(layers, item)
            return ::apply
        }
    }
}
