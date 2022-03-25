package hm.binkley.layers

interface MutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layers<K, V, M> {
    /** Modifies the current layer. */
    fun edit(block: EditMap<K, V>.() -> Unit)

    /**
     * Commits the current layer, and starts a new layer.  Implementations
     * decide the type of the new layer.
     *
     * @todo Returning M loses type information for K and V ?!
     */
    fun saveAndNext(name: String): MutableLayer<K, V, M>

    /**
     * Commits the current layer, and starts a new layer provided by [next].
     */
    fun <N : M> saveAndNext(next: () -> N): N

    /** Removes the current layer. */
    fun undo()
}
