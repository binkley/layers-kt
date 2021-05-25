package hm.binkley.layers.x

/** @todo How for M to extend L, inheriting layer-specific funcs/props? */
interface XMutableLayer<K : Any, V : Any, M : XMutableLayer<K, V, M>> :
    XLayer<K, V>, XLayerMutableMap<K, V> {
    @Suppress("UNCHECKED_CAST")
    val self: M
        get() = this as M

    fun edit(block: XEditBlock<K, V>): M

    @Suppress("UNCHECKED_CAST")
    fun <T : V> getValueAs(key: K): T = (this[key] as XValue<T>).value
}
