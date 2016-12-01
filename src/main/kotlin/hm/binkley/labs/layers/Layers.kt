package hm.binkley.labs.layers

import hm.binkley.labs.layers.rules.Rule
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

    fun layers(): List<Map<*, *>> = layers

    private fun save(layer: Layer<*>) {
        layers.add(layer)
        updateCache()
    }

    private fun updateCache() {
        val updated: MutableSet<Any> = LinkedHashSet()
        layers.flatMap { it.keys }.
                distinct().
                forEach {
                    updated.add(it)
                    cache[it] = value(it)
                }
        cache.keys.retainAll(updated)
    }

    private fun value(key: Any): Any {
        return layers.
                filter { it.containsKey(key) }.
                filter { it[key] is Rule<*, *> }.
                map { it[key] as Rule<*, *> }.
                last().
                invoke(this.RuleSurface(key))!!
    }

    companion object {
        data class Return<L : Layer<L>>(val layers: Layers, val layer: L)

        fun <L : Layer<L>> firstLayer(firstLayer: (LayerSurface) -> L): Return<L> {
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

    inner class RuleSurface internal constructor(val key: Any) {
        @Suppress("UNCHECKED_CAST")
        fun <T> values(): List<T> {
            return layers.
                    filter {it.contains(key) }.
                    filter { it[key] !is Rule<*, *> }.
                    map { it[key] as T }
        }
    }
}
