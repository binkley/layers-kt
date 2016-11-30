package hm.binkley.labs

abstract class Layer<L : Layer<L>>(protected val layers: Layers.LayersSurface) : LayerView {
    fun view(): LayerView = this

    fun <K : Layer<K>> saveAndNext(next: (layers: Layers.LayersSurface) -> K): K
            = layers.saveAndNext(this, next)
}
