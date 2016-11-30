package hm.binkley.labs

import java.util.*
import java.util.function.Consumer

class Layers private constructor(
        private val layers: MutableList<Layer<*>>)
    : List<Map<Any, Any>> by layers {
    companion object {
        fun <L : Layer<L>> firstLayer(ctor: (Layers.LayersSurface) -> Layer<L>,
                holder: Consumer<Layers>): Layer<L> {
            val layers = Layers(ArrayList<Layer<*>>())
            holder.accept(layers)
            return ctor(layers.LayersSurface())
        }
    }

    inner class LayersSurface internal constructor() {
        fun <K : Layer<K>> saveAndNext(layer: Layer<*>,
                next: (Layers.LayersSurface) -> K): K {
            layers.add(layer)
            return next(this)
        }
    }
}
