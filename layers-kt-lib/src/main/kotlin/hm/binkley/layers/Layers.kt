package hm.binkley.layers

open class Layers {
    private val savedLayers = mutableListOf<Map<String, Any>>()
    private var currentLayer = mutableMapOf<String, Any>()

    /**
     * An immutable view of all layers, including the current layer (editable)
     * layer, in order from newest to oldest.  The current layer is the
     * newest.
     */
    val layers: List<Map<String, Any>>
        get() {
            val layers = ArrayList(savedLayers)
            layers.add(0, currentLayer)
            return layers
        }
}
