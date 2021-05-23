package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.x.util.XArrayMutableStack
import hm.binkley.layers.x.util.XMutableStack
import hm.binkley.layers.x.util.XStack
import hm.binkley.layers.x.util.mutableStackOf
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

/** @todo Make Spotbugs happy about foo.collectionOp().toCollectionType() */
@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class XLayers<K : Any, V : Any, M : XMutableLayer<K, V, M>>(
    private val firstLayerName: String = "<INIT>",
    private val defaultLayer: (String) -> M,
    private val layers: XMutableStack<M> = mutableStackOf(
        defaultLayer(firstLayerName)
    ),
) : AbstractMap<K, V>() {
    val history: XStack<XLayer<K, V>> get() = layers

    fun <T : V> newRule(
        name: String,
        compute: () -> T,
    ): XRule<V, T> = object : XRule<V, T>(name) {
        override fun invoke(
            values: List<T>,
            layers: XLayers<*, V, *>,
        ) = compute()
    }

    fun <T : V> newRule(
        name: String,
        compute: (List<T>) -> T,
    ): XRule<V, T> = object : XRule<V, T>(name) {
        override fun invoke(
            values: List<T>,
            layers: XLayers<*, V, *>,
        ) = compute(values)
    }

    /** Convenience function for editing the current layer. */
    fun edit(block: XEditBlock<K, V>): M = layers.peek().edit(block)

    /** Commit the current layer, and begin a new one with [defaultLayer]. */
    fun <N : M> commitAndNext(nextLayer: () -> N): N {
        val next = nextLayer()
        layers.push(next)
        return next
    }

    /**
     * Commit the current layer, and begin a new one with [nextLayer] having
     * [name].
     */
    fun <N : M> commitAndNext(name: String, nextLayer: (String) -> N): N =
        commitAndNext { nextLayer(name) }

    /**
     * Commit the current layer, and begin a new one with [defaultLayer]
     * having [name].
     */
    fun commitAndNext(name: String): M = commitAndNext(name, defaultLayer)

    /** Discard the current layer. */
    fun rollback(): M {
        layers.pop()
        return layers.peek()
    }

    /** Try "what-if" scenarios without mutating the current layer. */
    fun whatIf(
        name: String = "<WHAT-IF>",
        block: XEditBlock<K, V>,
    ): Map<K, V> {
        val layers = XLayers(
            defaultLayer = defaultLayer,
            layers = XArrayMutableStack(layers),
        )
        layers.commitAndNext(name).edit(block)
        return layers
    }

    override val entries: Set<Entry<K, V>>
        get() = object : AbstractSet<Entry<K, V>>() {
            private val keys: Set<K> = allKeys()
            override val size: Int get() = keys.size

            override fun iterator(): Iterator<Entry<K, V>> =
                object : AbstractIterator<Entry<K, V>>() {
                    private val kit: Iterator<K> = keys.iterator()

                    override fun computeNext() {
                        if (!kit.hasNext()) done()
                        else {
                            val key = kit.next()
                            setNext(SimpleEntry(key, computeValue(key)))
                        }
                    }
                }
        }

    override fun toString() = history.mapIndexed { index, layer ->
        "$index: $layer"
    }.joinToString("\n")

    private fun allKeys() = layers.flatMap { it.keys }.toSet()

    private fun computeValue(key: K): V {
        val rule = currentRuleFor<V>(key)
        val values = currentValuesFor<V>(key)

        return rule(values, this)
    }

    private fun <T : V> currentRuleFor(key: K): XRule<V, T> =
        valuesOrRules(key).filterIsInstance<XRule<V, T>>().first()

    private fun <T : V> currentValuesFor(key: K): List<T> =
        valuesOrRules(key).filterIsInstance<XValue<T>>().map { it.value }

    private fun valuesOrRules(key: K): List<XValueOrRule<V>> =
        layers.mapNotNull { it[key] }.reversed()
}
