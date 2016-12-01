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
        data class Return<L : Layer<L>>(val layers: Layers, val layer: L)

        fun <L : Layer<L>> firstLayer(firstLayer: (Layers.LayersSurface) -> L): Return<L> {
            val layers = Layers(ArrayList())
            return Return(layers, firstLayer(layers.LayersSurface()))
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
