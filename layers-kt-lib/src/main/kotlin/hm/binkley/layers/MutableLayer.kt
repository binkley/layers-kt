package hm.binkley.layers

interface MutableLayer<L : MutableLayer<L>> : Layer<L>, LayerMutableMap {
    fun edit(block: EditBlock): L {
        block()
        return self
    }
}
