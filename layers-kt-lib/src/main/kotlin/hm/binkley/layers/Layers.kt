package hm.binkley.layers

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Pass in block to update the initial current layer
 */
class Layers(
    private val _layers: MutableList<EditableLayer>,
) {
    val layers: List<Layer> get() = _layers
    val current get(): EditableLayer = _layers.first()

    operator fun get(key: String) = calculateValue(key)

    fun saveAndNew(block: MutableMap<String, Entry<*>>.() -> Unit = {}):
        EditableLayer {
            val new = EditableLayer()
            new.edit(block)
            _layers.add(0, new)
            return new
        }

    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: (${layer::class.simpleName}) $layer"
    }.joinToString("\n")

    private fun calculateValue(key: String) = findRule(key)(findValues(key))

    private fun findRule(key: String): Rule<*> {
        try {
            return _layers
                .mapNotNull { it[key] }
                .filterIsInstance<Rule<*>>()
                .first()
        } catch (e: NoSuchElementException) {
            val x = IllegalArgumentException("No rule: $key")
            x.stackTrace = e.stackTrace
            throw x
        }
    }

    private fun <T> findValues(key: String): List<T> = _layers
        .mapNotNull { it[key] }
        .filterIsInstance<Value<T>>()
        .map { it.value }

    companion object {
        fun new() = Layers(mutableListOf(EditableLayer()))
        fun new(layers: List<EditableLayer>) = Layers(layers.toMutableList())
        fun new(
            block: MutableMap<String, Entry<*>>.() -> Unit,
        ) = Layers(mutableListOf(EditableLayer().edit(block)))
    }
}
