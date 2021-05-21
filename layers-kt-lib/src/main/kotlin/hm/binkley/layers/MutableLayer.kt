package hm.binkley.layers

interface MutableLayer<L : MutableLayer<L>> : Layer<L>, LayerMutableMap {
    fun edit(vararg changes: LayerPair): L = edit(changes.toMap())

    fun edit(changes: LayerMap): L {
        this += changes
        return self
    }

    fun edit(block: EditBlock): L {
        block()
        return self
    }
}
