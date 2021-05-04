package hm.binkley.layers.x

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry

private typealias Layer<K, V> = MutableMap<K, ValueOrRule<V>>
private typealias LayerEntry<K, V> = MutableEntry<K, ValueOrRule<V>>

open class MutableMapList<K, V>(
    val history: MutableList<Layer<K, V>> = mutableListOf(),
) : AbstractMutableMap<K, ValueOrRule<V>>(),
    MutableList<Layer<K, V>> by history {
    init {
        // TODO: Always start with 1 map?
        if (history.isEmpty()) history.add(mutableMapOf())
    }

    private val current: Layer<K, V> get() = history.last()

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
        for (map in history.reversed())
            for (key in map.keys)
                if (unseenKeys.add(key)) entries.add(computeEntry(key))
        return entries
    }

    override val size: Int get() = current.size
    override fun isEmpty(): Boolean = current.isEmpty()
    override val entries: MutableSet<LayerEntry<K, V>> = current.entries

    override fun put(key: K, value: ValueOrRule<V>): ValueOrRule<V>? =
        current.put(key, value)

    override fun clear() = current.clear()

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    override fun toString() = history.mapIndexed { i, it ->
        "$i: $it"
    }.joinToString("\n")
}
