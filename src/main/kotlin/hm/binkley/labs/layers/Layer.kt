package hm.binkley.labs.layers

import java.util.*

abstract class Layer<L : Layer<L>>(
        val name: String,
        protected val layers: Layers.LayerSurface,
        private var map: MutableMap<Any, Any> = LinkedHashMap())
    : Map<Any, Any> by map {
    fun put(key: Any, value: Any): L {
        map[key] = value
        return self()
    }

    override fun toString(): String = name + ": " + map.toString()

    operator fun set(key: Any, value: Any) = map.put(key, value)

    fun <K : Layer<K>> saveAndNext(next: (layers: Layers.LayerSurface) -> K): K {
        // TODO: Suboptimally throw RTE when modifying after save
        map = Collections.unmodifiableMap(map)
        return layers.saveAndNext(this, next)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun self(): L = this as L
}
