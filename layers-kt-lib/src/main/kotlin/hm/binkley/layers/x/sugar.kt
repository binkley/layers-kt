package hm.binkley.layers.x

typealias XLayerMap<K, V> = Map<K, XValueOrRule<V>>
typealias XLayerMutableMap<K, V> = MutableMap<K, XValueOrRule<V>>
typealias XEditBlock<K, V> = XLayerMutableMap<K, V>.() -> Unit
