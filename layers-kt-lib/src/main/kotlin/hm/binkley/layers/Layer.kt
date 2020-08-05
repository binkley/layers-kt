package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.toDisplay
import hm.binkley.layers.Layers.LayerSurface
import java.util.Collections
import javax.annotation.processing.Generated

abstract class Layer<L : Layer<L>>(
    protected val layers: LayerSurface,
    val name: String,
    private var map: MutableMap<Any, Any> = mutableMapOf(),
) : Map<Any, Any> by map {
    @Suppress("UNCHECKED_CAST")
    fun <T> getAs(key: Any): T = get(key) as T // TODO: Clash with Map.get

    fun put(key: Any, value: Any): L {
        map[key] = value
        return self()
    }

    @Generated // Lie to JaCoCo
    override fun toString(): String = "$name: ${toDisplay(map)}"

    operator fun set(key: Any, value: Any) = map.put(key, value)

    fun <K : Layer<K>> saveAndNext(next: (layers: LayerSurface) -> K): K {
        // TODO: Suboptimally throw RTE when modifying after save
        map = map.unmodifiable()
        return layers.saveAndNext(this, next)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun self(): L = this as L

    private fun <K, V> MutableMap<K, V>.unmodifiable() =
        Collections.unmodifiableMap(this)
}
