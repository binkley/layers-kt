package hm.binkley.layers

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

    override operator fun get(key: String) = calculateValue(key)

    override val entries
        get() = _layers.flatMap {
            it.keys
        }.distinct().map {
            SimpleEntry(it, calculateValue(it))
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

    private fun calculateValue(key: String) = findRule(key)(findValues(key))

    private fun findRule(key: String): Rule<Any> {
        try {
            return _layers
                .mapNotNull { it[key] }
                .filterIsInstance<Rule<Any>>()
                .first()
        } catch (e: NoSuchElementException) {
            val x = IllegalArgumentException("No rule for key: $key")
            x.stackTrace = e.stackTrace
            throw x
        }
    }

    private fun <T> findValues(key: String): List<T> = _layers
        .mapNotNull { it[key] }
        .filterIsInstance<Value<T>>()
        .map { it.value }

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
