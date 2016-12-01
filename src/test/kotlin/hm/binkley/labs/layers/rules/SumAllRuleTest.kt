package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layer
import hm.binkley.labs.layers.Layers
import hm.binkley.labs.layers.Layers.Companion.firstLayer
import hm.binkley.labs.layers.ScratchLayer
import hm.binkley.labs.layers.rules.Rule.Companion.sumAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SumAllRuleTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldSumAllLayers() {
        firstLayer.
                put("A", sumAll()).
                saveAndNext(::ScratchLayer).
                put("A", 1).
                saveAndNext(::ScratchLayer).
                put("A", 2).
                saveAndNext(::ScratchLayer)

        assertEquals(3, layers.get("A"))
    }
}
