package hm.binkley.labs

import hm.binkley.labs.Layers.Companion.firstLayer
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LayerTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        firstLayer = firstLayer(::ScratchLayer)
        { layers -> this.layers = layers }
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

        val e: UnsupportedOperationException
                = assertThrows(UnsupportedOperationException::class.java)
        { firstLayer["B"] = 2; }
    }
}
