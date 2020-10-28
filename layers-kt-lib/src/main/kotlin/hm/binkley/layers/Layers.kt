package hm.binkley.layers

open class Layers(
    private val layers: List<Layer> = mutableListOf(EditableLayer()),
) : List<Layer> by layers {
    val current = layers.first() as EditableLayer
}
