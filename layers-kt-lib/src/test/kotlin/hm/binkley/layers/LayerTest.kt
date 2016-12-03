package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.firstLayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LayerTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldKnowName() {
        assertEquals("Bob", firstLayer.
                saveAndNext({ layers -> EgLayer("Bob", layers) }).name)
    }

    @Test
    fun shouldChainPutting() {
        assertSame(firstLayer, firstLayer.put("A", 1))
    }

    @Test
    fun shouldGetWhatIsPut() {
        firstLayer["A"] = 1
        assertSame(1, firstLayer["A"])
    }

    @Test
    fun shouldComplainIfModifyLayerAfterSaved() {
        firstLayer.saveAndNext(::ScratchLayer)

        assertThrows<Exception>(UnsupportedOperationException::class.java)
        { firstLayer["B"] = 2; }
    }

    class EgLayer(name: String, layers: Layers.LayerSurface) : Layer<EgLayer>(name, layers)
}
