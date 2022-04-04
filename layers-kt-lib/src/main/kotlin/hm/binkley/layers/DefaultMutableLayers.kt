package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.DefaultMutableLayer.Companion.defaultMutableLayer
import hm.binkley.util.MutableStack
import hm.binkley.util.Stack
import hm.binkley.util.stackOf
import hm.binkley.util.toMutableStack
import hm.binkley.util.top
import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
open class DefaultMutableLayers<K : Any, V : Any, M : MutableLayer<K, V, M>>(
    override val name: String,
    private val defaultMutableLayer: (String) -> M,
    initLayers: Stack<M> = stackOf(defaultMutableLayer("<INIT>")),
) : MutableLayers<K, V, M>, AbstractMap<K, V>() {
    private val layers: MutableStack<M> = initLayers.toMutableStack()

    companion object {
        @Suppress("UPPER_BOUND_VIOLATED_WARNING", "TYPE_MISMATCH_WARNING")
        fun <K : Any, V : Any> defaultMutableLayers(
            name: String,
        ): MutableLayers<K, V, *> =
            DefaultMutableLayers<K, V, MutableLayer<K, V, *>>(
                name, { defaultMutableLayer<K, V>(it) }
            )
    }

    override val entries: Set<Entry<K, V>> get() = ViewSet()
    override val history: Stack<Layer<K, V, M>> = layers
    override val current: M get() = layers.top

    /**
     * Directly compute the value for [key], rather than find the entries
     * (key/value pairs) for all keys.
     * Most rules do not need to compute the values for other keys.
     */
    @SuppressFBWarnings("NP_NONNULL_RETURN_VIOLATION")
    override fun get(key: K): V = computeValue(key)

    override fun whatIfWith(block: EditMap<K, V>.() -> Unit):
        Layers<K, V, M> {
        val whatIf = DefaultMutableLayers(name, defaultMutableLayer, layers)
        whatIf.saveAndNext("<WHAT-IF>").edit(block)
        return whatIf
    }

    override fun whatIfWithout(except: Collection<Layer<K, V, *>>):
        Layers<K, V, M> = without(except)

    override fun edit(block: EditMap<K, V>.() -> Unit): Unit =
        DefaultLayersEditMap().block()

    override fun saveAndNext(name: String): M = saveAndNext {
        defaultMutableLayer(name)
    }

    override fun <N : M> saveAndNext(next: () -> N): N {
        val layer = next()
        layers.push(layer)
        return layer
    }

    override fun undo() {
        layers.pop()
    }

    override fun toString(): String = history.mapIndexed { index, layer ->
        "$index (${layer::class.simpleName}): $layer"
    }.joinToString("\n", "$name: ${super.toString()}\n")

    private fun without(except: Collection<Layer<*, *, *>>):
        DefaultMutableLayers<K, V, M> {
        if (except.isEmpty()) return this

        val layers: MutableStack<M> = layers.toMutableStack()
        layers.removeAll(except.toSet())
        return DefaultMutableLayers(name, defaultMutableLayer, layers)
    }

    private fun <T : V> computeValue(key: K): T {
        val rule = currentRuleFor<T>(key)
        val values = currentValuesFor<T>(key)

        return rule(key, values, this)
    }

    private fun <T : V> currentRuleFor(key: K): Rule<K, V, T> =
        valuesOrRules(key).filterIsInstance<Rule<K, V, T>>().last()

    private fun <T : V> currentValuesFor(key: K): Sequence<T> =
        valuesOrRules(key).asSequence()
            .filterIsInstance<Value<T>>()
            .map { it.value }

    private fun valuesOrRules(key: K): List<ValueOrRule<V>> =
        layers.mapNotNull { it[key] }

    private fun allKeys(): Set<K> = history.flatMap { it.keys }.toSet()

    private inner class ViewSet : AbstractSet<Entry<K, V>>() {
        private val keys: Set<K> = allKeys()

        override val size: Int get() = keys.size
        override fun iterator(): Iterator<Entry<K, V>> = ViewIterator()

        private inner class ViewIterator : Iterator<Entry<K, V>> {
            private val kit = keys.iterator()

            override fun hasNext(): Boolean = kit.hasNext()
            override fun next(): Entry<K, V> {
                val key = kit.next()
                return SimpleEntry(key, computeValue(key))
            }
        }
    }

    private inner class DefaultLayersEditMap :
        EditMap<K, V>, MutableMap<K, ValueOrRule<V>> by layers.top
}
