package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.DefaultMutableLayer.Companion.defaultMutableLayer
import hm.binkley.layers.util.MutableStack
import hm.binkley.layers.util.Stack
import hm.binkley.layers.util.mutableStackOf
import hm.binkley.layers.util.toMutableStack
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

interface Layers<K : Any, V : Any, L : Layer<K, V, L>> : Map<K, V> {
    val name: String
    val history: Stack<Map<K, ValueOrRule<V>>>
    val current: L

    fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V>

    /** @todo Layers parameter as `L` loses type information ?! */
    fun whatIfWithout(layer: Layer<*, *, *>): Map<K, V>
}

interface MutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>> :
    Layers<K, V, M> {
    fun edit(block: LayersEditMap<K, V>.() -> Unit)

    /** @todo Returning M loses type information for K and V ?! */
    fun commitAndNext(name: String): MutableLayer<K, V, M>
    fun <N : M> commitAndNext(nextMutableLayer: () -> N): N
}

@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class DefaultMutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>>(
    override val name: String,
    // TODO: If initLayers provided, there's no point to this
    firstLayerName: String = "<INIT>",
    initLayers: List<M> = listOf(),
    private val defaultMutableLayer: (String) -> M,
) : MutableLayers<K, V, M>, AbstractMap<K, V>() {
    private val layers: MutableStack<M> = mutableStackOf()

    init {
        if (initLayers.isEmpty())
            layers.add(defaultMutableLayer(firstLayerName))
        else layers.addAll(initLayers)
    }

    companion object {
        fun <K : Any, V : Any> defaultMutableLayers(
            name: String,
        ): MutableLayers<K, V, *> =
            DefaultMutableLayers<K, V, MutableLayer<K, V, *>>(name) {
                defaultMutableLayer<K, V>(it)
            }
    }

    override val entries: Set<Entry<K, V>> get() = ViewSet()
    override val history: Stack<Map<K, ValueOrRule<V>>> = layers
    override val current: M get() = layers.peek()

    override fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V> {
        val whatIf = DefaultMutableLayers(
            name, "<INIT>", layers, defaultMutableLayer
        )
        whatIf.commitAndNext("<WHAT-IF>").edit(block)
        return whatIf
    }

    override fun whatIfWithout(layer: Layer<*, *, *>): Map<K, V> {
        val layers: MutableStack<M> = layers.toMutableStack()
        layers.remove(layer)
        return DefaultMutableLayers(
            name,
            "<INIT>",
            initLayers = layers,
            defaultMutableLayer
        )
    }

    override fun edit(block: LayersEditMap<K, V>.() -> Unit) =
        DefaultLayersEditMap().block()

    override fun commitAndNext(name: String): M = commitAndNext {
        defaultMutableLayer(name)
    }

    override fun <N : M> commitAndNext(nextMutableLayer: () -> N): N {
        val layer = nextMutableLayer()
        layers.push(layer)
        return layer
    }

    override fun toString() = history.mapIndexed { index, layer ->
        "$index: $layer"
    }.joinToString("\n", "$name: ${super.toString()}\n")

    private fun <T : V> currentRuleFor(key: K): Rule<K, V, T> =
        valuesOrRules(key).filterIsInstance<Rule<K, V, T>>().last()

    private fun <T : V> currentValuesFor(key: K): List<T> =
        valuesOrRules(key).filterIsInstance<Value<T>>().map { it.value }

    private fun valuesOrRules(key: K): List<ValueOrRule<V>> =
        layers.mapNotNull { it[key] }

    private inner class ViewIterator(keys: Set<K>) : Iterator<Entry<K, V>> {
        private val kit = keys.iterator()

        override fun hasNext(): Boolean = kit.hasNext()
        override fun next(): Entry<K, V> {
            val key = kit.next()
            return SimpleEntry(key, computeValue(key))
        }
    }

    private inner class ViewSet(val keys: Set<K> = allKeys()) :
        AbstractSet<Entry<K, V>>() {
        override val size: Int get() = keys.size
        override fun iterator(): Iterator<Entry<K, V>> = ViewIterator(keys)
    }

    private inner class ViewMap(private val except: K) : AbstractMap<K, V>() {
        override val entries: Set<Entry<K, V>>
            get() = ViewSet(allKeys().filterNot { it == except }.toSet())
    }

    private fun allKeys(): Set<K> = history.flatMap { it.keys }.toSet()
    private fun computeValue(key: K): V {
        val rule = currentRuleFor<V>(key)
        val values = currentValuesFor<V>(key)

        return rule(key, values, ViewMap(key))
    }

    private inner class DefaultLayersEditMap :
        LayersEditMap<K, V>, MutableMap<K, ValueOrRule<V>> by layers.peek() {
        @Suppress("UNCHECKED_CAST")
        @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
        override fun <T : V> getOtherValueAs(key: K): T =
            this@DefaultMutableLayers[key] as T
    }
}
