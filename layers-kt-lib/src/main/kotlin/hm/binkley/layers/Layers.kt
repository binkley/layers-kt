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
     * *NB* &mdash; this function would be nicer as an extension function on
     * the interface, but would then require caller to provide [K], [V], and
     * [L].
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : V> getAs(key: K, except: List<Layer<K, V, *>> = listOf()): T =
        whatIfWithout(except)[key] as T

    /**
     * Creates a map (after rules applied) as-if there were an additional,
     * topmost layer defined by [block], suitable for compare/contrast of
     * changes without modifying these layers.
     */
    fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V>

    /**
     * Creates a map (after rules applied) as-if these layers did not
     * include the [except] list.  The default list is the [current] layer;
     * this default is suitable for compare/contrast of ongoing edits against
     * the current, topmost layer.
     */
    fun whatIfWithout(except: List<Layer<K, V, *>> = listOf(current)): Map<K, V>
}

interface MutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layers<K, V, M> {
    fun edit(block: EditMap<K, V>.() -> Unit)

    /** @todo Returning M loses type information for K and V ?! */
    fun saveAndNext(name: String): MutableLayer<K, V, M>
    fun <N : M> saveAndNext(next: () -> N): N

    /** Removes the most recent layer. */
    fun undo()
}
