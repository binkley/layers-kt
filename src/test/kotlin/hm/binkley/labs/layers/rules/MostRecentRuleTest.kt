package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layer
import hm.binkley.labs.layers.Layers
import hm.binkley.labs.layers.Layers.Companion.firstLayer
import hm.binkley.labs.layers.ScratchLayer
import hm.binkley.labs.layers.rules.Rule.Companion.mostRecent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MostRecentRuleTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldGetMostRecentValue() {
        firstLayer.
                put("A", mostRecent(4)).
                saveAndNext(::ScratchLayer).
                put("A", 1).
                saveAndNext(::ScratchLayer).
                put("A", 2).
                saveAndNext(::ScratchLayer)

        assertEquals(2, layers["A"])
    }

    @Test
    fun shouldDefaultRecentValue() {
        firstLayer.
                put("A", mostRecent(4)).
                saveAndNext(::ScratchLayer)

        assertEquals(4, layers["A"])
    }
}
