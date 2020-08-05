package hm.binkley.layers

import hm.binkley.layers.Layers.LayerSurface
import hm.binkley.layers.rules.Rule.Companion.sumAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class LayersTest : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    @Test
    fun `should chain saving and assign to super type`() {
        firstLayer.saveAndNext(::ScratchLayer).saveAndNext(::FinalLayer)
    }

    @Test
    fun `should have history`() {
        firstLayer.saveAndNext(::ScratchLayer)

        assertEquals(listOf(firstLayer), layers.layers())
    }

    @Disabled("TODO: Warning is correct: Delegating to 'var' property" +
            " does not take its changes into account")
    @Test
    fun `should reject updates after saved`() {
        firstLayer.put("A", sumAll())
        val secondLayer = firstLayer.saveAndNext(::ScratchLayer)
        secondLayer.saveAndNext(::FinalLayer)

        assertThrows<IllegalStateException> {
            secondLayer.put("A", 1)
        }
    }

    open class SubLayer<L : SubLayer<L>>(layers: LayerSurface, name: String) :
        Layer<L>(layers, name)

    class FinalLayer(layers: LayerSurface) :
        SubLayer<FinalLayer>(layers, "Final")
}
