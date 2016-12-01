package hm.binkley.labs

import java.util.*

abstract class Layer<L : Layer<L>>(
        protected val layers: Layers.LayersSurface,
        private var map: MutableMap<Any, Any> = LinkedHashMap())
    : Map<Any, Any> by map {
    fun put(key: Any, value: Any): L {
        map[key] = value
        return self()
    }

    operator fun set(key: Any, value: Any) = map.put(key, value)

    fun <K : Layer<K>> saveAndNext(next: (layers: Layers.LayersSurface) -> K): K {
        // TODO: Suboptimally throw RTE when modifying after save
        map = Collections.unmodifiableMap(map)
        return layers.saveAndNext(this, next)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun self(): L = this as L
}
