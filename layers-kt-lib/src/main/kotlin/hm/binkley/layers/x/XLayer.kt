package hm.binkley.layers.x

interface XLayer<K : Any, V : Any> : XLayerMap<K, V> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    fun <T : V> getValueAs(key: K): T = (this[key] as XValue<T>).value
}
