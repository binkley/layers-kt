package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.x.util.XArrayMutableStack
import hm.binkley.layers.x.util.XMutableStack
import hm.binkley.layers.x.util.XStack
import hm.binkley.layers.x.util.mutableStackOf
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

/**
 * A [Map] view of all values in all layers _after_ applying rules for each
 * list of values.  A `Layers` begins with one empty layer to edit.
 *
 * To all layers immutably in order of committal, use [history].
 *
 * @todo Make Spotbugs happy about foo.collectionOp().toCollectionType()
 */
@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class XLayers<K : Any, V : Any, M : XMutableLayer<K, V, M>>(
    firstLayerName: String = "<INIT>",
    private val defaultLayer: (String, () -> XEditMap<K, V>) -> M,
    private val layers: XMutableStack<M> = mutableStackOf(),
) : AbstractMap<K, V>(), XRules<K, V> {
    init {
        // TODO: Avoid this ugly way to support deep cloning
        if (layers.isEmpty()) commitAndNext(firstLayerName)
    }

    /**
     * An immutable stack view in committal order of each layer with entry
     * values as layer values or rules.
     */
    val history: XStack<XLayer<K, V, M>> get() = layers

    /** Gets the current-most, mutable layer. */
    fun peek(): M = layers.last()

    override fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: () -> T,
    ): XRule<K, V, T> = object : XRule<K, V, T>(key, name) {
        override fun invoke(
            key: K,
            values: List<T>,
            layers: XLayers<K, V, *>,
        ) = computeValue()
    }

    override fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (List<T>) -> T,
    ): XRule<K, V, T> = object : XRule<K, V, T>(key, name) {
        override fun invoke(
            key: K,
            values: List<T>,
            layers: XLayers<K, V, *>,
        ) = computeValue(values)
    }

    override fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (List<T>, XLayers<K, V, *>) -> T,
    ): XRule<K, V, T> = object : XRule<K, V, T>(key, name) {
        override fun invoke(
            key: K,
            values: List<T>,
            layers: XLayers<K, V, *>,
        ) = computeValue(values, layers)
    }

    override fun <T : V> newRule(
        key: K,
        name: String,
        computeValue: (K, List<T>, XLayers<K, V, *>) -> T,
    ): XRule<K, V, T> = object : XRule<K, V, T>(key, name) {
        override fun invoke(
            key: K,
            values: List<T>,
            layers: XLayers<K, V, *>,
        ) = computeValue(key, values, layers)
    }

    /**
     * @todo Should there be a runtime exception for adding values to a key
     *       having a constant rule?
     */
    override fun <T : V> constantRule(key: K, value: T): XRule<K, V, T> =
        newRule(key, "Constant(value=$value)") { -> value }

    override fun <T : V> latestOfRule(key: K, default: T): XRule<K, V, T> =
        newRule(key, "Latest(default=$default)") { values ->
            values.firstOrNull() ?: default
        }

    /**
     * Edits the current layer as a mutable map.  This layer is not yet
     * committed.
     */
    fun edit(block: XEditBlock<K, V>): M = layers.peek().edit(block)

    /**
     * Commits the current layer, and begins a new layer with [nextLayer]
     * given [name].
     */
    fun <N : M> commitAndNext(
        name: String,
        nextLayer: (String, () -> XEditMap<K, V>) -> N,
    ): N {
        val next = nextLayer(name, ::EditMap)
        layers.push(next)
        return next
    }

    /**
     * Commits the current layer, and begins a new one with [defaultLayer]
     * given [name].
     */
    fun commitAndNext(name: String): M = commitAndNext(name, defaultLayer)

    /** Discards the current layer. */
    fun rollback(): M {
        layers.pop()
        return layers.peek()
    }

    /**
     * Tries "what-if" scenarios without mutating the current layer.  The
     * returned map behaves as if "this" had [block] applied to
     * a new default layer.
     */
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

    // Short-circuit computing all keys to avoid circular calls for rules
    override fun get(key: K): V? = computeValue(key)

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

        return rule(key, values, this)
    }

    private fun <T : V> currentRuleFor(key: K): XRule<K, V, T> =
        valuesOrRules(key).filterIsInstance<XRule<K, V, T>>().first()

    private fun <T : V> currentValuesFor(key: K): List<T> =
        valuesOrRules(key).filterIsInstance<XValue<T>>().map { it.value }

    private fun valuesOrRules(key: K): List<XValueOrRule<V>> =
        layers.mapNotNull { it[key] }.reversed()

    private inner class EditMap :
        XLayerMutableMap<K, V> by peek(),
        XRules<K, V> by this,
        XEditMap<K, V>
}
