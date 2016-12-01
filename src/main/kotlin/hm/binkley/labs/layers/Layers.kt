package hm.binkley.labs.layers

import java.util.*

class Layers private constructor(
        private val layers: MutableList<Layer<*>>,
        private val cache: MutableMap<Any, Any> = LinkedHashMap())
    : Map<Any, Any> by cache {
    init {
        updateCache()
    }

    override fun toString(): String {
        var toString = "All: " + cache.toString()
        layers.withIndex().reversed().
                map { "" + (it.index + 1) + ": " + it.value }.
                forEach { toString += "\n" + it }
        return toString
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

        fun <L : Layer<L>> firstLayer(firstLayer: (Layers.LayerSurface) -> L): Return<L> {
            val layers = Layers(ArrayList())
            return Return(layers, firstLayer(layers.LayerSurface()))
        }
    }

    inner class LayerSurface internal constructor() {
        fun <K : Layer<K>> saveAndNext(layer: Layer<*>,
                next: (Layers.LayerSurface) -> K): K {
            save(layer)
            return next(this)
        }
    }
}
