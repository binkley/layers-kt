package hm.binkley.layers.x

typealias XEditBlock<K, V> = XEditMap<K, V>.() -> Unit
typealias XLayerMap<K, V> = Map<K, XValueOrRule<V>>
typealias XLayerMutableMap<K, V> = MutableMap<K, XValueOrRule<V>>

fun <T : Any> T.toValue() = XValue(this)
