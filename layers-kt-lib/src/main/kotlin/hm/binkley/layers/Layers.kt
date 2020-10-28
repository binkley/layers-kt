package hm.binkley.layers

/**
 * Creates a new list of editable layers, with an initial blank editable
 * layer.
 *
 * @todo Pass in block to update the initial current layer
 */
class Layers(
    private val layers: MutableList<EditableLayer>,
) : List<Layer> by layers {
    val current get() = layers.first()

    fun saveAndNew(block: MutableMap<String, Entry<*>>.() -> Unit = {}):
        EditableLayer {
            val new = EditableLayer()
            new.edit(block)
            layers.add(0, new)
            return new
        }

    override fun toString() = layers.mapIndexed { index, layer ->
        "$index: (${layer::class.simpleName}) $layer"
    }.joinToString("\n")

    companion object {
        fun new() = Layers(mutableListOf(EditableLayer()))
        fun new(layers: List<EditableLayer>) = Layers(layers.toMutableList())
        fun new(
            block: MutableMap<String, Entry<*>>.() -> Unit,
        ) = Layers(mutableListOf(EditableLayer().edit(block)))
    }
}
