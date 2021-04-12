package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import java.util.AbstractMap.SimpleEntry

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Pass in block to update the initial current layer
 * @todo History, metadata
 */
class Layers(
    private val _layers: MutableList<MutableLayer>,
) : AbstractMap<String, Any>() {
    val layers: List<Layer> get() = _layers
    val current get(): MutableLayer = _layers[0]

    init {
        validate()
    }

    override operator fun get(key: String) = calculate<Any>(key)
    override val entries get() = entries()

    /**
     * Commits the current layer (it will no longer be editable), and pushes
     * on a new, blank layer possibly modified by [block] (default no-op).
     */
    fun commitAndNext(
        name: String,
        block: MutableMap<String, Entry<*>>.() -> Unit = {},
    ): MutableLayer {
        val new = MutablePlainLayer(name).edit(block)
        _layers.add(0, new)

        validate()

        return new
    }

    /** Poses a "what-if" [scenario]. */
    fun whatIf(scenario: MutableLayer): Layers {
        val _layers = ArrayList(_layers)
        _layers.add(0, scenario)
        return Layers(_layers)
    }

    /**
     * Poses a "what-if" scenario named "<WHAT-IF>".
     *
     * @see [whatIf]
     */
    fun whatIf(block: MutableMap<String, Entry<*>>.() -> Unit): Layers =
        whatIf(MutablePlainLayer("<WHAT-IF>").edit(block))

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: [${layer::class.simpleName}] $layer"
    }.joinToString("\n")

    // TODO: Inline refactor once SpotBugs is sorted out
    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    private fun entries() = _layers.flatMap {
        it.keys
    }.distinct().map {
        SimpleEntry(it, calculate<Any>(it))
    }.toSet()

    @Suppress("UNCHECKED_CAST")
    private fun <T> calculate(key: String): T {
        var rule: Rule<T>? = null
        val values = ArrayList<T>(_layers.size)

        for (layer in _layers) {
            val entry = layer[key] ?: continue
            when (entry) {
                is Rule<*> -> if (null == rule) rule = (entry as Rule<T>)
                else -> values.add((entry as Value<T>).value)
            }
        }

        requireNotNull(rule) { "No rule for key: $key" }

        return rule(values, this)
    }

    private fun validate() {
        require(_layers.isNotEmpty()) {
            "Layers must begin with an editable layer"
        }

        val keysAndRules = mutableMapOf<String, Entry<*>>()
        _layers.forEach { keysAndRules += it }
        for ((key, entry) in keysAndRules)
            require(entry is Rule<*>) {
                "A rule must proceed values for key: $key"
            }
    }

    companion object {
        fun new(
            vararg firstLayer: Pair<String, Entry<*>>,
        ) = new {
            firstLayer.forEach {
                this[it.first] = it.second
            }
        }

        fun new(layers: List<MutableLayer>) = Layers(layers.toMutableList())

        /** @todo Enforce that first layer must have rules, not values */
        fun new(
            name: String = "<INIT>",
            block: MutableMap<String, Entry<*>>.() -> Unit,
        ) = Layers(mutableListOf(MutablePlainLayer(name).edit(block)))
    }
}
