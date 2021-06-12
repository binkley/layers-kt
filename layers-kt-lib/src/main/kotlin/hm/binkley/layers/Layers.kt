package hm.binkley.layers

import hm.binkley.layers.util.Stack

interface Layers<K : Any, V : Any, L : Layer<K, V, L>> : Map<K, V> {
    val name: String
    val history: Stack<Layer<K, V, *>>

    /** @todo Returning L loses type information for K and V ?! */
    val current: Layer<K, V, L>

    /**
     * Returns the typed value of another [key] as computed by layers.  Use
     * [except] to compute without certain layers.
     *
     * @todo Merge into [whatIfWithout]
     */
    fun <T : V> getAs(key: K, except: List<Layer<K, V, *>> = listOf()): T

    fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V>
    fun whatIfWithout(except: List<Layer<*, *, *>>): Map<K, V>
}

interface MutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layers<K, V, M> {
    fun edit(block: EditMap<K, V>.() -> Unit)

    /** @todo Returning M loses type information for K and V ?! */
    fun commitAndNext(name: String): MutableLayer<K, V, M>
    fun <N : M> commitAndNext(next: () -> N): N

    /** Removes the most recent layer. */
    fun rollback()
}
