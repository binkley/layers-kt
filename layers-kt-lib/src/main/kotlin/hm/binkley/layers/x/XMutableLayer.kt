package hm.binkley.layers.x

interface XMutableLayer<K : Any, V : Any, M : XMutableLayer<K, V, M>> :
    XLayer<K, V, M>, XLayerMutableMap<K, V> {
    fun edit(block: XEditBlock<K, V>): M
}
