package hm.binkley.layers

import hm.binkley.layers.util.Stack

interface Layers<K : Any, V : Any, L : Layer<K, V, L>> : Map<K, V> {
    val name: String
    val history: Stack<Layer<K, V, *>>
    val current: L

    fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V>
    fun whatIfWithout(layer: Layer<*, *, *>): Map<K, V>
}

interface MutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layers<K, V, M> {
    fun edit(block: LayersEditMap<K, V>.() -> Unit)

    /** @todo Returning M loses type information for K and V ?! */
    fun commitAndNext(name: String): MutableLayer<K, V, M>
    fun <N : M> commitAndNext(next: (LayersEditMap<K, V>) -> N): N

    /** Removes the most recent layer. */
    fun rollback()
}
