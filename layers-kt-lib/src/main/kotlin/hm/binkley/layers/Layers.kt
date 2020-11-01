package hm.binkley.layers

import hm.binkley.layers.RuleCalculation.Companion.lookingForRule
import java.util.AbstractMap.SimpleEntry

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Pass in block to update the initial current layer
 * @todo Be a mutable map, where changed edit the top layer
 */
class Layers(
    private val _layers: MutableList<EditableLayer>,
) : AbstractMap<String, Any>() {
    val layers: List<Layer> get() = _layers
    val current get(): EditableLayer = _layers[0]

    override operator fun get(key: String) = calculate<Any>(key)

    override val entries
        get() = _layers.flatMap {
            it.keys
        }.distinct().map {
            SimpleEntry(it, calculate<Any>(it))
        }.toSet()

    fun saveAndNew(block: MutableMap<String, Entry<*>>.() -> Unit = {}):
        EditableLayer {
            val new = EditableLayer().edit(block)
            _layers.add(0, new)
            return new
        }

    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: (${layer::class.simpleName}) $layer"
    }.joinToString("\n")

    private fun <T> calculate(key: String) = _layers
        .mapNotNull { it[key] }
        .fold<Entry<*>, RuleCalculation<T>>(lookingForRule(key)) { acc, e ->
            @Suppress("UNCHECKED_CAST")
            acc.add(e as Entry<T>)
        }
        .calculate()

    companion object {
        fun new(vararg firstLayer: Pair<String, Entry<*>>) =
            new(mutableListOf(EditableLayer(mutableMapOf(*firstLayer))))

        fun new(layers: List<EditableLayer>) = Layers(layers.toMutableList())

        /** @todo Enforce that first layer must have rules, not values */
        fun new(
            block: MutableMap<String, Entry<*>>.() -> Unit,
        ) = Layers(mutableListOf(EditableLayer().edit(block)))
    }
}
