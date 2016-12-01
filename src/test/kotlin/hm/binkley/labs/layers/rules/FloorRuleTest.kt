package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layer
import hm.binkley.labs.layers.Layers
import hm.binkley.labs.layers.Layers.Companion.firstLayer
import hm.binkley.labs.layers.ScratchLayer
import hm.binkley.labs.layers.rules.Rule.Companion.floor
import hm.binkley.labs.layers.rules.Rule.Companion.sumAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FloorRuleTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldFloor() {
        firstLayer.
                put("A", sumAll()).
                saveAndNext(::ScratchLayer).
                put("A", 1).
                saveAndNext(::ScratchLayer).
                put("A", 2).
                saveAndNext(::ScratchLayer).
                put("A", floor(4)).
                saveAndNext(::ScratchLayer)

        assertEquals(4, layers["A"])
    }

    @Test
    fun shouldIgnoreFloor() {
        firstLayer.
                put("A", sumAll()).
                saveAndNext(::ScratchLayer).
                put("A", 1).
                saveAndNext(::ScratchLayer).
                put("A", 2).
                saveAndNext(::ScratchLayer).
                put("A", floor(2)).
                saveAndNext(::ScratchLayer)

        assertEquals(3, layers["A"])
    }
}
