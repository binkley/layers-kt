package hm.binkley.layers.x

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class XDefaultMutableLayer<K : Any, V : Any, M : XDefaultMutableLayer<K, V, M>>(
    name: String,
    private val editMap: () -> XEditMap<K, V>,
    map: XLayerMutableMap<K, V> = mutableMapOf(),
) : XDefaultLayer<K, V, M>(name, map),
    XMutableLayer<K, V, M>,
    XLayerMutableMap<K, V> by map {
    override fun edit(block: XEditBlock<K, V>): M {
        editMap().block()
        return self
    }

    companion object {
        fun <K : Any, V : Any> defaultMutableLayer(): (String, () -> XEditMap<K, V>) -> XDefaultMutableLayer<K, V, *> =
            { name, editMap ->
                XDefaultMutableLayer<K, V, XDefaultMutableLayer<K, V, *>>(
                    name, editMap
                )
            }
    }
}
