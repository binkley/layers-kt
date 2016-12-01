package hm.binkley.labs.layers

import java.util.*

class Layers private constructor(
        private val layers: MutableList<Layer<*>>,
        private val cache: MutableMap<Any, Any> = LinkedHashMap())
    : Map<Any, Any> by cache {
    init {
        updateCache()
    }

    fun layers(): List<Map<Any, Any>> = layers

    private fun save(layer: Layer<*>) {
        layers.add(layer)
        updateCache()
    }

    private fun updateCache() {
        layers.forEach { cache.putAll(it) }
    }

    companion object {
        fun <L : Layer<L>> firstLayer(ctor: (Layers.LayersSurface) -> Layer<L>,
                holder: (Layers) -> Unit): Layer<L> {
            val layers = Layers(ArrayList())
            holder.invoke(layers)
            return ctor(layers.LayersSurface())
        }
    }

    inner class LayersSurface internal constructor() {
        fun <K : Layer<K>> saveAndNext(layer: Layer<*>,
                next: (Layers.LayersSurface) -> K): K {
            save(layer)
            return next(this)
        }
    }
}
