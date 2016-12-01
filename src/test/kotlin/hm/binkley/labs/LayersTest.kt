package hm.binkley.labs

import hm.binkley.labs.Layers.Companion.firstLayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LayersTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        firstLayer = firstLayer(::ScratchLayer)
        { layers -> this.layers = layers }
    }

    @Test
    fun shouldChainSavingAndAssignToSuperType() {
        val layer: SubLayer<*> = firstLayer.
                saveAndNext(::ScratchLayer).
                saveAndNext(::FinalLayer)
    }

    @Test
    fun shouldHaveHistory() {
        firstLayer.saveAndNext(::ScratchLayer)

        assertEquals(listOf(firstLayer), layers.layers())
    }

    @Test
    fun shouldGetWhatIsPutInALayer() {
        firstLayer.
                put("A", 1).
                saveAndNext(::ScratchLayer)

        assertEquals(1, layers.get("A"))
    }

    open class SubLayer<L : SubLayer<L>>(layers: Layers.LayersSurface) : Layer<L>(layers)
    class FinalLayer(layers: Layers.LayersSurface) : SubLayer<FinalLayer>(layers)
}
