package hm.binkley.layers.x

import java.util.AbstractMap.SimpleEntry
import kotlin.collections.Map.Entry

interface Layer<K, V> : Map<K, ValueOrRule<V>>
interface MutableLayer<K, V> : Layer<K, V>, MutableMap<K, ValueOrRule<V>>

/**
 * Reads like a map of current computed values.  Edits like a mutable map of
 * the currentmost map.  Supports list operations of maps.
 *
 * @todo Generic for L and ML
 */
open class Layers<K, V> : AbstractMap<K, V>() {
    private val layers = mutableListOf<MutableLayer<K, V>>()

    fun history(): List<Layer<K, V>> = layers

    fun <ML : MutableLayer<K, V>> add(new: ML): ML {
        layers.add(new)
        return new
    }

    // TODO: Will this work for subtypes of MutableLayer without caller
    //       specifying the generic type?
    fun <ML : MutableLayer<K, V>> edit(block: ML.() -> Unit): ML {
        @Suppress("UNCHECKED_CAST")
        val current = layers.last() as ML
        current.block()
        return current
    }

    override val entries: Set<Entry<K, V>>
        get() = keysForComputing().map { key ->
            SimpleEntry<K, V>(key, computeFor(key))
        }.toSet()

    private fun keysForComputing(): Set<K> {
        val keys = mutableSetOf<K>()
        for (layer in layers) keys.addAll(layer.keys)
        return keys
    }

    private fun <RuleV : V> computeFor(key: K): RuleV {
        val rule: Rule<K, V, RuleV> = ruleFor(key)
        val values: List<RuleV> = valuesFor(key)

        return rule(key, values, this)
    }

    private fun <RuleV : V> ruleFor(key: K): Rule<K, V, RuleV> {
        for (layer in layers) when (val value = layer[key]) {
            null -> continue
            is Value<*> -> throw IllegalStateException("Value before rule: $key")
            else ->
                @Suppress("UNCHECKED_CAST")
                return value as Rule<K, V, RuleV>
        }
        throw NullPointerException("Missing rule: $key")
    }

    private fun <RuleV : V> valuesFor(key: K): List<RuleV> {
        val values = mutableListOf<RuleV>()
        for (layer in layers) when (val value = layer[key]) {
            null -> continue
            is Value<*> ->
                @Suppress("UNCHECKED_CAST")
                values.add(value.value as RuleV)
            else -> continue
        }
        return values
    }
}
