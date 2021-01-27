package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.RuleCalculation.Companion.lookingForRule
import java.util.AbstractMap.SimpleEntry

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Pass in block to update the initial current layer
 * @todo Be a mutable map, where changed edit the top layer
 * @todo History, metadata, what-if calculations
 */
class Layers(
    private val _layers: MutableList<MutableLayer>,
) : AbstractMap<String, Any>() {
    val layers: List<Layer> get() = _layers
    val current get(): MutableLayer = _layers[0]

    override operator fun get(key: String) = calculate<Any>(key)
    override val entries get() = entries()

    /**
     * Commits the current layer (it will no longer be editable), and pushes
     * on a new, blank layer.
     */
    fun saveAndNew(
        name: String,
        block: MutableMap<String, Entry<*>>.() -> Unit = {},
    ): MutableLayer {
        val new = MutablePlainLayer(name).edit(block)
        _layers.add(0, new)
        return new
    }

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: [${layer::class.simpleName}] $layer"
    }.joinToString("\n")

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    private fun <T> calculate(key: String) = _layers
        .mapNotNull { it[key] }
        .fold<Entry<*>, RuleCalculation<T>>(
            lookingForRule(key, this)
        ) { acc, e ->
            @Suppress("UNCHECKED_CAST")
            acc.add(e as Entry<T>)
        }
        .calculate()

    /*
    // TODO: Fewer traversals through same data multiple times
    @Suppress("UNCHECKED_CAST")
    private fun calculate(key: String, _layers: List<Map<String, Entry<*>>>): Any {
        var rule: Rule<Any>? = null
        val values = ArrayList<Any>(top.size)

        for (layer in _layers) {
            val Entry = layer[key] ?: continue
            when (Entry) {
                is Rule<*> -> rule = (Entry as Rule<Any>)
                else -> values.add((Entry as Value<Any>).value)
            }
        }

        if (null == rule) error("No rule for key: $key")

        return rule(key, values.asReversed(), _layers)
    }
    */

    // TODO: Inline refactor once SpotBugs is sorted out
    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    private fun entries() = _layers.flatMap {
        it.keys
    }.distinct().map {
        SimpleEntry(it, calculate<Any>(it))
    }.toSet()

    companion object {
        fun new(
            vararg firstLayer: Pair<String, Entry<*>>,
        ) = new {
            firstLayer.forEach {
                this[it.first] = it.second
            }
        }

        fun new(layers: List<MutableLayer>) =
            Layers(layers.toMutableList())

        /** @todo Enforce that first layer must have rules, not values */
        fun new(
            name: String = "<INIT>",
            block: MutableMap<String, Entry<*>>.() -> Unit,
        ) = Layers(mutableListOf(MutablePlainLayer(name).edit(block)))
    }
}
