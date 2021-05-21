package hm.binkley.layers.x

open class XDefaultMutableLayer<K, V : Any, M : XDefaultMutableLayer<K, V, M>>(
    name: String,
) : XDefaultLayer<K, V, M>(name),
    XMutableLayer<K, V, M> {
    override fun edit(block: XEditBlock<K, V>): M {
        block(map)
        return self
    }

    companion object {
        fun <K, V : Any> defaultMutableLayer(): (String) -> XDefaultMutableLayer<K, V, *> =
            { name: String ->
                XDefaultMutableLayer<K, V, XDefaultMutableLayer<K, V, *>>(name)
            }
    }
}
