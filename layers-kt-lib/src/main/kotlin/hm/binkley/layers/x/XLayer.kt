package hm.binkley.layers.x

interface XLayer<K, V : Any> : XLayerMap<K, V> {
    val name: String
}
