package hm.binkley.layers

open class DefaultMutableLayer<K : Any, V : Any, M : DefaultMutableLayer<K, V, M>>(
    override val name: String,
    private val map: MutableMap<K, ValueOrRule<V>> = mutableMapOf(),
) : MutableLayer<K, V, M>, MutableMap<K, ValueOrRule<V>> by map {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <K : Any, V : Any> defaultMutableLayer(name: String):
            DefaultMutableLayer<K, V, *> =
            DefaultMutableLayer<K, V, DefaultMutableLayer<K, V, *>>(name)
    }

    final override fun edit(block: EditMap<K, V>.() -> Unit): M {
        DefaultEditMap().block()
        return self
    }

    override fun toString(): String = "$name: $map"

    private inner class DefaultEditMap :
        EditMap<K, V>, MutableMap<K, ValueOrRule<V>> by map
}
