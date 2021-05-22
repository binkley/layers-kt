package hm.binkley.layers.x

interface XLayer<K : Any, V : Any> : XLayerMap<K, V> {
    val name: String
}
