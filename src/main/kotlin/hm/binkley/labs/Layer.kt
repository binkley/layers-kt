package hm.binkley.labs

import java.util.*

abstract class Layer<L : Layer<L>>(protected val layers: Layers.LayersSurface) : LayerView {
    private val values: MutableMap<Any, Any> = LinkedHashMap()

    fun view(): LayerView = this

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: Any): T = values[key] as T

    fun put(key: Any, value: Any): L {
        values[key] = value
        return self()
    }

    fun <K : Layer<K>> saveAndNext(next: (layers: Layers.LayersSurface) -> K): K
            = layers.saveAndNext(this, next)

    @Suppress("UNCHECKED_CAST")
    protected fun self(): L = this as L
}

operator fun Layer<*>.set(key: Any, value: Any) = put(key, value)
