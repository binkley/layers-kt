package hm.binkley.layers.x

/** @todo How for M to extends L, and also be a MutableLayer? */
interface XMutableLayer<K : Any, V : Any, M : XMutableLayer<K, V, M>> :
    XLayer<K, V> {
    @Suppress("UNCHECKED_CAST")
    val self: M
        get() = this as M

    fun edit(block: XEditBlock<K, V>): M
}
