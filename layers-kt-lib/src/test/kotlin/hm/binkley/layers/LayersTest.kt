package hm.binkley.layers

import hm.binkley.layers.Layers.LayerSurface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LayersTest : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    @Test
    fun shouldChainSavingAndAssignToSuperType() {
        @Suppress("UNUSED_VARIABLE")
        val layer: SubLayer<*> = firstLayer.saveAndNext(::ScratchLayer)
                .saveAndNext(::FinalLayer)
    }

    @Test
    fun shouldHaveHistory() {
        firstLayer.saveAndNext(::ScratchLayer)

        assertEquals(listOf(firstLayer), layers.layers())
    }

    open class SubLayer<L : SubLayer<L>>(layers: LayerSurface, name: String)
        : Layer<L>(layers, name)

    class FinalLayer(layers: LayerSurface)
        : SubLayer<FinalLayer>(layers, "Final")
}
