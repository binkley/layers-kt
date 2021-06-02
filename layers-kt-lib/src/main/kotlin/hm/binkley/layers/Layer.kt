package hm.binkley.layers

interface Layer<K : Any, V : Any, L : Layer<K, V, L>> :
    Map<K, ValueOrRule<V>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L
        get() = this as L
}

interface MutableLayer<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layer<K, V, M>,
    MutableMap<K, ValueOrRule<V>> {
    fun edit(block: EditMap<K, V>.() -> Unit): M
}
