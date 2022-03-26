package hm.binkley.layers

import hm.binkley.util.Stack

interface Layers<K : Any, V : Any, out L : Layer<K, V, L>> : Map<K, V> {
    val name: String
    val history: Stack<Layer<K, V, L>>

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
    fun whatIfWith(block: EditMap<K, V>.() -> Unit): Layers<K, V, L>

    /**
     * Creates a map (after rules applied) as-if these layers did not
     * include the [except] list.  The default list is the [current] layer;
     * this default is suitable for compare/contrast of ongoing edits against
     * the current, topmost layer.
     */
    fun whatIfWithout(
        except: List<Layer<K, V, *>> = listOf(current)
    ): Layers<K, V, L>
}
