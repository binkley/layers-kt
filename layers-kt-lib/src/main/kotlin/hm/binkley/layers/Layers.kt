package hm.binkley.layers

import hm.binkley.layers.rules.Rule
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.LinkedHashSet

class Layers private constructor(
        private val layers: MutableList<Layer<*>>,
        private val cache: MutableMap<Any, Any> = LinkedHashMap())
    : Map<Any, Any> by cache {
    init {
        updateCache()
    }

    override fun toString(): String {
        var toString = "All (${layers.size}): ${toDisplay(cache)}"
        layers.withIndex().reversed().
                map { "" + (it.index + 1) + ": " + it.value }.
                forEach { toString += "\n" + it }
        return toString
    }

    fun layers(): List<Map<*, *>> = layers

    @Suppress("UNCHECKED_CAST")
    fun <T> getT(key: Any): T = get(key) as T // TODO: Clash with Map.get

    private fun save(layer: Layer<*>) {
        layers.add(layer)
        updateCache()
    }

    private fun updateCache() {
        val updated = LinkedHashSet<Any>()
        layers.flatMap { it.keys }.
                distinct().
                forEach {
                    updated.add(it)
                    cache[it] = value(it)
                }
        cache.keys.retainAll(updated)
    }

    private fun value(key: Any): Any {
        val layer = layers.last { it[key] is Rule<*> }
        return (layer.getT<Rule<*>>(key)).
                invoke(this.RuleSurface(layer, key))!!
    }

    companion object {
        fun <L : Layer<L>> firstLayer(firstLayer: (LayerSurface) -> L)
                : Pair<Layers, L> {
            val layers = Layers(ArrayList())
            return Pair(layers, firstLayer(layers.LayerSurface()))
        }

        fun toDisplay(map: Map<Any, Any>): Map<Any, Any> = map.
                asSequence().
                map { Pair(simpleClassKey(it.key), it.value) }.
                toMap()

        private fun simpleClassKey(key: Any): Any = when (key) {
            is Class<*> -> "[${key.simpleName}]"
            else -> key
        }
    }

    inner class LayerSurface internal constructor() {
        fun <K : Layer<K>> saveAndNext(layer: Layer<*>,
                next: (LayerSurface) -> K): K {
            save(layer)
            return next(this)
        }

        fun <T> get(key: Any): T = getT(key)
    }

    inner class RuleSurface internal constructor(val layer: Layer<*>,
            val key: Any) {
        fun <T> values(): List<T> {
            return layers.
                    filter { it.contains(key) }.
                    filter { it[key] !is Rule<*> }.
                    map {
                        @Suppress("UNCHECKED_CAST")
                        it[key] as T
                    }
        }

        fun <R> without(): R {
            val without = ArrayList<Layer<*>>(layers)
            without.remove(layer)
            @Suppress("UNCHECKED_CAST")
            return Layers(without)[key] as R
        }
    }
}
