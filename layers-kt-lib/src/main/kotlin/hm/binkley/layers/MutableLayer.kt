package hm.binkley.layers

interface MutableLayer<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layer<K, V, M>, MutableMap<K, ValueOrRule<V>> {
    fun edit(block: EditMap<K, V>.() -> Unit): M
}
