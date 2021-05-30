package hm.binkley.layers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import java.util.AbstractMap.SimpleEntry

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Metadata
 */
class Layers(
    private val history: MutableList<MutableLayer<*>>,
) : AbstractMap<String, Any>() {
    val layers: List<Layer<*>> get() = history
    val current get(): MutableLayer<*> = history[0]

    init {
        validate()
    }

    override operator fun get(key: String) = calculate<Any>(key)
    override val entries get() = entries()

    /** Edits the current layer. */
    fun edit(block: EditBlock): Layers {
        current.edit(block)
        validate()
        return this
    }

    /**
     * Commits the current layer (it will no longer be editable), and pushes
     * on a new, blank layer possibly modified by [block] (default no-op).
     */
    fun commitAndNext(
        name: String,
        block: EditBlock = {},
    ): MutableLayer<*> {
        val new = DefaultMutableLayer(name).edit(block)
        history.add(0, new)

        validate()

        return new
    }

    /**
     * Commits the current layer (it will no longer be editable), and pushes
     * on [layer].
     */
    fun <L : MutableLayer<L>> commitAndNext(layer: L): L {
        history.add(0, layer)

        validate()

        return layer
    }

    /** Poses a "what-if" scenario. */
    fun whatIf(vararg scenario: LayerPair): Layers =
        whatIf(scenario.toMap())

    /** Poses a "what-if" [scenario]. */
    fun whatIf(scenario: LayerMap): Layers {
        val layers = ArrayList(history)
        layers.add(0, DefaultMutableLayer("<WHAT-IF>", scenario))
        return Layers(layers)
    }

    /** Poses a "what-if" scenario. */
    fun whatIf(block: EditBlock): Layers =
        whatIf(DefaultMutableLayer("<WHAT-IF>").edit(block))

    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: [${layer::class.simpleName}] $layer"
    }.joinToString("\n")

    /* @todo Inline refactor once SpotBugs is sorted out */
    @SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
    private fun entries() = history.flatMap {
        it.keys
    }.distinct().map {
        SimpleEntry(it, calculate<Any>(it))
    }.toSet()

    @Suppress("UNCHECKED_CAST")
    private fun <T> calculate(key: String): T {
        var rule: Rule<T>? = null
        val values = ArrayList<T>(history.size)

        for (layer in history) {
            val entry = layer[key] ?: continue
            when (entry) {
                is Rule<*> -> if (null == rule) rule = (entry as Rule<T>)
                else -> values.add((entry as Value<T>).value)
            }
        }

        requireNotNull(rule) { "No rule for key: $key" }

        return rule(key, values, this)
    }

    private fun validate() {
        val keysAndRules = mutableMapOf<String, ValueOrRule<*>>()
        history.forEach { keysAndRules += it }
        for ((key, entry) in keysAndRules)
            require(entry is Rule<*>) {
                "A rule must proceed values for key: $key"
            }
    }

    companion object {
        fun new(vararg firstLayer: LayerPair) = new {
            firstLayer.forEach {
                this[it.first] = it.second
            }
        }

        fun new(layers: List<MutableLayer<*>>) =
            Layers(layers.toMutableList())

        fun new(name: String = "<INIT>", block: EditBlock) =
            Layers(mutableListOf(DefaultMutableLayer(name).edit(block)))
    }
}
