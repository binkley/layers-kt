package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.firstLayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LayersTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldChainSavingAndAssignToSuperType() {
        @Suppress("UNUSED_VARIABLE")
        val layer: SubLayer<*> = firstLayer.
                saveAndNext(::ScratchLayer).
                saveAndNext(::FinalLayer)
    }

    @Test
    fun shouldHaveHistory() {
        firstLayer.saveAndNext(::ScratchLayer)

        assertEquals(listOf(firstLayer), layers.layers())
    }

    open class SubLayer<L : SubLayer<L>>(name: String, layers: Layers.LayerSurface) : Layer<L>(name, layers)
    class FinalLayer(layers: Layers.LayerSurface) : SubLayer<FinalLayer>("Final", layers)
}
