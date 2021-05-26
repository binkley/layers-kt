package hm.binkley.layers.x

interface XEditMap<K : Any, V : Any> : XLayerMutableMap<K, V>, XRules<K, V> {
    fun <T : V> T.toValue() = XValue(this)
}
