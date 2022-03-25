package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

open class DefaultMutableLayer<K : Any, V : Any, M : DefaultMutableLayer<K, V, M>>(
    override val name: String,
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val map: MutableMap<K, ValueOrRule<V>> = mutableMapOf(),
) : MutableLayer<K, V, M>, MutableMap<K, ValueOrRule<V>> by map {
    companion object {
        @Suppress("UNCHECKED_CAST", "UPPER_BOUND_VIOLATED_WARNING")
        @SuppressFBWarnings("EI_EXPOSE_REP2")
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
