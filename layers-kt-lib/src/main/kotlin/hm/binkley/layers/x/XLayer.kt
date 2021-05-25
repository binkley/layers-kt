package hm.binkley.layers.x

interface XLayer<K : Any, V : Any, L : XLayer<K, V, L>> : XLayerMap<K, V> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L
        get() = this as L

    @Suppress("UNCHECKED_CAST")
    fun <T : V> getValueAs(key: K): T = (this[key] as XValue<T>).value
}
