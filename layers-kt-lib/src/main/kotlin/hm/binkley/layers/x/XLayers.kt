package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

/** @todo Make Spotbugs happy about foo.collectionOp().toCollectionType() */
@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class XLayers<K, V : Any, M : XMutableLayer<K, V, M>>(
    firstLayerName: String = "<INIT>",
    private val defaultMutableLayer: (String) -> M,
    private val _layers: MutableList<M> = mutableListOf(
        defaultMutableLayer(firstLayerName)
    ),
) : AbstractMap<K, V>() {
    val layers: List<XLayer<K, V>> get() = _layers

    fun edit(block: XEditBlock<K, V>): M = _layers.first().edit(block)

    fun whatIf(block: XEditBlock<K, V>): Map<K, V> {
        val layers = XLayers(
            defaultMutableLayer = defaultMutableLayer,
            _layers = ArrayList(_layers),
        )
        layers.commitAndNext("<WHAT-IF>").edit(block)
        return layers
    }

    fun <N : M> commitAndNext(nextLayer: () -> N): N {
        val next = nextLayer()
        _layers.add(0, next)
        return next
    }

    fun <N : M> commitAndNext(name: String, nextLayer: (String) -> N): N =
        commitAndNext { nextLayer(name) }

    fun commitAndNext(name: String): M =
        commitAndNext(name, defaultMutableLayer)

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

    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: $layer"
    }.joinToString("\n")

    private fun allKeys() = _layers.flatMap { it.keys }.toSet()

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
        _layers.mapNotNull { it[key] }
}
