package hm.binkley.layers

/** A read-only map of [Value]s and/or [Rule]s. */
interface Layer<K : Any, V : Any, out L : Layer<K, V, L>> :
    Map<K, ValueOrRule<V>> {
    /** The name of this layer. */
    val name: String

    /** Convenience for returning `this` as the exact type of [L]. */
    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}
