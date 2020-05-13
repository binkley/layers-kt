package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.Layers.LayerSurface
import org.junit.jupiter.api.BeforeEach

abstract class LayersTestSupport<L : Layer<L>>(
    private val ctor: (LayerSurface) -> L
) {
    lateinit var layers: Layers
    lateinit var firstLayer: L

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(ctor)
        this.layers = layers
        this.firstLayer = firstLayer
    }
}
