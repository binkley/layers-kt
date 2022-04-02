package hm.binkley.layers

/** A mutable map of [Value]s and/or [Rule]s.
 * Use [edit] to mutate.
 */
interface MutableLayer<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layer<K, V, M>, MutableMap<K, ValueOrRule<V>> {
    /** Edits this layer by mutating an [EditMap] in the given [block]. */
    fun edit(block: EditMap<K, V>.() -> Unit): M
}
