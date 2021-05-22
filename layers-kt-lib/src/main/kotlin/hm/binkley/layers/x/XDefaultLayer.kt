package hm.binkley.layers.x

open class XDefaultLayer<K : Any, V : Any, L : XDefaultLayer<K, V, L>>(
    override val name: String,
    protected val map: XLayerMutableMap<K, V> = mutableMapOf(),
) : XLayer<K, V>,
    XLayerMap<K, V> by map {
    override fun toString(): String = "${this::class.simpleName}[$name]: $map"
}
