package hm.binkley.layers.x

interface Layer<K : Any, V : Any, L : Layer<K, V, L>> :
    Map<K, ValueOrRule<V>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L
        get() = this as L
}

interface MutableLayer<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layer<K, V, M>,
    MutableMap<K, ValueOrRule<V>> {
    fun edit(block: EditMap<K, V>.() -> Unit)
}

open class DefaultMutableLayer<K : Any, V : Any, M : DefaultMutableLayer<K, V, M>>(
    override val name: String,
    private val map: MutableMap<K, ValueOrRule<V>> = mutableMapOf(),
) : MutableLayer<K, V, M>, MutableMap<K, ValueOrRule<V>> by map {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <K : Any, V : Any> defaultMutableLayer(
            name: String,
        ): DefaultMutableLayer<K, V, *> =
            DefaultMutableLayer<K, V, DefaultMutableLayer<K, V, *>>(name)
    }

    override fun edit(block: EditMap<K, V>.() -> Unit) =
        LayerEditMap().block()

    override fun toString(): String = "$name: $map"

    private inner class LayerEditMap :
        EditMap<K, V>, MutableMap<K, ValueOrRule<V>> by map {
        @Suppress("UNCHECKED_CAST")
        override fun <T : V> getOtherValueAs(key: K): T =
            (this[key] as Value<T>).value
    }
}
