package hm.binkley.layers

open class Layers {
    private var currentLayer = MutableLayer()

    // TODO: Unify current and saved layers with current as topmost
    private val savedLayers = mutableListOf<Layer>()

    /**
     * An immutable view of all layers, including the current layer (editable)
     * layer, in order from newest to oldest.  The current layer is the
     * newest.  This is treated as a _queue_, not a _stack_.
     */
    val layers: List<Map<String, Any>>
        get() {
            val layers = ArrayList(savedLayers)
            layers.add(0, currentLayer)
            return layers
        }
}
