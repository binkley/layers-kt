package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

typealias XEditBlock<K, V> = MutableMap<K, XValueOrRule<V>>.() -> Unit

interface XLayer<K, V : Any> : Map<K, XValueOrRule<V>> {
    val name: String
}

/** @todo How for M to extends L, and also be a MutableLayer? */
interface XMutableLayer<K, V : Any, M : XMutableLayer<K, V, M>> :
    XLayer<K, V> {
    @Suppress("UNCHECKED_CAST")
    val self: M
        get() = this as M

    fun edit(block: XEditBlock<K, V>): M
}

@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class XLayers<K, V : Any, M : XMutableLayer<K, V, M>>(
    firstLayerName: String = "<INIT>",
    private val defaultMutableLayer: (String) -> M,
) : AbstractMap<K, V>() {
    private val _layers: MutableList<M> =
        mutableListOf(defaultMutableLayer(firstLayerName))

    val layers: List<XLayer<K, V>> get() = _layers

    fun edit(block: XEditBlock<K, V>): M = _layers.first().edit(block)

    fun commitAndNext(name: String): M =
        commitAndNext(name, defaultMutableLayer)

    /** @todo Provide `defaultLayer` as default argument for `nextLayer` */
    fun <N : M> commitAndNext(name: String, nextLayer: (String) -> N): N {
        val next = nextLayer(name)
        _layers.add(0, next)
        return next
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

    private fun allKeys() = _layers.flatMap { it.keys }.toSet()

    private fun computeValue(key: K): V {
        val rule = currentRuleFor<V>(key)
        val values = currentValuesFor<V>(key)

        return rule(values, this)
    }

    private fun <T : V> currentRuleFor(key: K): XRule<V, T> =
        valuesOrRules(key).filterIsInstance<XRule<V, T>>().first()

    private fun <T : V> currentValuesFor(key: K): List<T> =
        valuesOrRules(key)
            .filterIsInstance<XValue<T>>()
            .map { it.value }

    private fun valuesOrRules(key: K): List<XValueOrRule<V>> =
        _layers.mapNotNull { it[key] }
}

open class DefaultLayer<K, V : Any, L : DefaultLayer<K, V, L>>(
    override val name: String,
    protected val map: MutableMap<K, XValueOrRule<V>> = mutableMapOf(),
) : XLayer<K, V>,
    Map<K, XValueOrRule<V>> by map {
    override fun toString(): String = "${this::class.simpleName}[$name]: $map"
}

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
