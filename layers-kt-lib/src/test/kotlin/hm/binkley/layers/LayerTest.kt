package hm.binkley.layers

import hm.binkley.layers.Layers.LayerSurface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class LayerTest : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    @Test
    fun shouldKnowName() = assertEquals("Bob", firstLayer.
            saveAndNext({ layers -> EgLayer(layers, "Bob") }).name)

    @Test
    fun shouldChainPutting() = assertSame(firstLayer, firstLayer.put("A", 1))

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

    class EgLayer(layers: LayerSurface, name: String) : Layer<EgLayer>(layers, name)
}
