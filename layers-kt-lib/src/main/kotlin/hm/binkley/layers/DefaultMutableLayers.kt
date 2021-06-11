package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.util.MutableStack
import hm.binkley.layers.util.Stack
import hm.binkley.layers.util.emptyStack
import hm.binkley.layers.util.mutableStackOf
import hm.binkley.layers.util.toMutableStack
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

/** @todo _Either_ `firstLayerName` or `initLayers`, not both */
@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class DefaultMutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>>(
    override val name: String,
    firstLayerName: String = "<INIT>",
    initLayers: Stack<M> = emptyStack(),
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
                DefaultMutableLayer.defaultMutableLayer<K, V>(it)
            }
    }

    override val entries: Set<Entry<K, V>> get() = ViewSet()
    override val history: Stack<Layer<K, V, *>> = layers
    override val current: M get() = layers.peek()

    override fun <T : V> getAs(key: K, except: List<Layer<K, V, *>>): T =
        computeValue(key, except)

    override fun whatIfWith(block: EditMap<K, V>.() -> Unit): Map<K, V> {
        val whatIf = DefaultMutableLayers(
            name, "<INIT>", layers, defaultMutableLayer
        )
        whatIf.commitAndNext("<WHAT-IF>").edit(block)
        return whatIf
    }

    override fun whatIfWithout(except: List<Layer<*, *, *>>): Map<K, V> =
        without(except)

    override fun edit(block: LayersEditMap<K, V>.() -> Unit) =
        DefaultLayersEditMap().block()

    override fun commitAndNext(name: String): M = commitAndNext {
        defaultMutableLayer(name)
    }

    override fun <N : M> commitAndNext(next: (LayersEditMap<K, V>) -> N): N {
        val layer = next(DefaultLayersEditMap())
        layers.push(layer)
        return layer
    }

    override fun rollback() {
        layers.pop()
    }

    override fun toString() = history.mapIndexed { index, layer ->
        "$index (${layer::class.simpleName}): $layer"
    }.joinToString("\n", "$name: ${super.toString()}\n")

    private fun without(except: List<Layer<*, *, *>>):
        DefaultMutableLayers<K, V, M> {
        val layers: MutableStack<M> = layers.toMutableStack()
        layers.removeAll(except)
        return DefaultMutableLayers(
            name,
            "<INIT>",
            initLayers = layers,
            defaultMutableLayer
        )
    }

    private fun <T : V> computeValue(
        key: K,
        except: List<Layer<K, V, *>> = listOf(),
    ): T {
        val whatIf = without(except)
        val rule = whatIf.currentRuleFor<T>(key)
        val values = whatIf.currentValuesFor<T>(key)

        return rule(key, values, this)
    }

    private fun <T : V> currentRuleFor(key: K): Rule<K, V, T> =
        valuesOrRules(key).filterIsInstance<Rule<K, V, T>>().last()

    private fun <T : V> currentValuesFor(key: K): List<T> =
        valuesOrRules(key).filterIsInstance<Value<T>>().map { it.value }

    private fun valuesOrRules(
        key: K,
        layers: Stack<M> = this.layers,
    ): List<ValueOrRule<V>> =
        layers.mapNotNull { it[key] }

    private fun allKeys(): Set<K> = history.flatMap { it.keys }.toSet()

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

    private inner class DefaultLayersEditMap :
        LayersEditMap<K, V>, MutableMap<K, ValueOrRule<V>> by layers.peek() {
        override fun <T : V> getAs(key: K, except: List<Layer<K, V, *>>): T =
            computeValue(key, except)
    }
}
