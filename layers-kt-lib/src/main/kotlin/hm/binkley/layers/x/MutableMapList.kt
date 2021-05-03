package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry

class MutableMapList<K, V>(
    val history: MutableList<MutableMap<K, ValueOrRule<V>>> = mutableListOf(),
) : AbstractMutableMap<K, ValueOrRule<V>>(),
    MutableList<MutableMap<K, ValueOrRule<V>>> by history {
    init {
        // TODO: Always start with 1 map?
        if (history.isEmpty()) add(mutableMapOf())
    }

    private val current: MutableMap<K, ValueOrRule<V>> get() = history.last()

    fun view() = object : AbstractMap<K, V>() {
        override val entries: Set<Entry<K, V>>
            get() = computeEntries()
    }

    private fun computeEntries(): Set<Entry<K, V>> {
        @Suppress("UNCHECKED_CAST")
        fun computeEntry(key: K): Entry<K, V> {
            var rule: Rule<K, V>? = null
            val values: MutableList<V> = mutableListOf()

            for (map in history.reversed()) {
                when (val curr: ValueOrRule<V>? = map[key]) {
                    null -> continue
                    is Value<*> -> values += (curr as Value<V>).value
                    else -> if (null == rule) rule = curr as Rule<K, V>
                }
            }

            if (null == rule) throw IllegalStateException("No rule found for '$key'")
            val value = rule(values, this)

            return SimpleEntry(key, value)
        }

        val unseenKeys = mutableSetOf<K>()
        val entries = mutableSetOf<Entry<K, V>>()
        for (map in history)
            for (key in map.keys)
                if (unseenKeys.add(key)) entries.add(computeEntry(key))
        return entries
    }

    override val size: Int get() = current.size
    override fun isEmpty(): Boolean = current.isEmpty()
    override val entries: MutableSet<MutableEntry<K, ValueOrRule<V>>> =
        current.entries

    override fun put(key: K, value: ValueOrRule<V>): ValueOrRule<V>? =
        current.put(key, value)

    override fun clear() = current.clear()

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    override fun toString() = history.mapIndexed { i, it ->
        "$i: $it"
    }.joinToString("\n")
}

/** @todo Replace with [hm.binkley.layers.ValueOrRule] once typing is sorted out */
interface ValueOrRule<V>

/** @todo Replace with [hm.binkley.layers.Value] once typing is sorted out */
data class Value<V>(val value: V) : ValueOrRule<V>

/** @todo Replace with [hm.binkley.layers.Rule] once typing is sorted out */
abstract class Rule<K, V> :
    ValueOrRule<V>, (List<V>, MutableMapList<K, V>) -> V
