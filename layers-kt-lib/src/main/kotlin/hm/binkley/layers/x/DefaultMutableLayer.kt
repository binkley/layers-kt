package hm.binkley.layers.x

open class DefaultMutableLayer<K, V : Any, M : DefaultMutableLayer<K, V, M>>(
    name: String,
) : DefaultLayer<K, V, M>(name),
    XMutableLayer<K, V, M> {
    override fun edit(block: XEditBlock<K, V>): M {
        block(map)
        return self
    }

    companion object {
        fun <K, V : Any> defaultMutableLayer(): (String) -> DefaultMutableLayer<K, V, *> =
            { name: String ->
                DefaultMutableLayer<K, V, DefaultMutableLayer<K, V, *>>(name)
            }
    }
}
