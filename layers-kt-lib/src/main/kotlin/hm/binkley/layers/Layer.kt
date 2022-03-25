package hm.binkley.layers

interface Layer<K : Any, V : Any, out L : Layer<K, V, L>> :
    Map<K, ValueOrRule<V>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L
        get() = this as L
}
