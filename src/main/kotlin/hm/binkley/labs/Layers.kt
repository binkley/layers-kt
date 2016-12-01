package hm.binkley.labs

import java.util.*

class Layers private constructor(
        private val layers: MutableList<Layer<*>>)
    : List<Map<Any, Any>> by layers {
    companion object {
        fun <L : Layer<L>> firstLayer(ctor: (Layers.LayersSurface) -> Layer<L>,
                holder: (Layers) -> Unit): Layer<L> {
            val layers = Layers(ArrayList<Layer<*>>())
            holder.invoke(layers)
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
