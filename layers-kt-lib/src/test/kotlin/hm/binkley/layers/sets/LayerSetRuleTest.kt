package hm.binkley.layers.sets

import hm.binkley.layers.Layers
import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.rules.Rule.Companion.layerSet
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import hm.binkley.layers.sets.LayerSetCommand.Companion.add
import hm.binkley.layers.sets.LayerSetCommand.Companion.remove
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LayerSetRuleTest {
    lateinit var layers: Layers
    lateinit var firstLayer: ScratchLayer

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
    }

    @Test
    fun shouldAdd() {
        firstLayer.
                put("A", layerSet<ScratchLayer>(unlimited())).
                saveAndNext(::ScratchLayer).
                put("A", add(firstLayer)).
                saveAndNext(::ScratchLayer)

        assertEquals(setOf(firstLayer), layers["A"])
    }

    @Test
    fun shouldRemove() {
        firstLayer.
                put("A", layerSet<ScratchLayer>(unlimited())).
                saveAndNext(::ScratchLayer).
                put("A", add(firstLayer)).
                saveAndNext(::ScratchLayer).
                put("A", remove(firstLayer)).
                saveAndNext(::ScratchLayer)

        assertTrue(layers.getT<LayerSet<*>>("A").isEmpty())
    }
}
