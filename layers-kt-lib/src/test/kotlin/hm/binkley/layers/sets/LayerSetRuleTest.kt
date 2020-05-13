package hm.binkley.layers.sets

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.rules.Rule.Companion.layerSet
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import hm.binkley.layers.sets.LayerSetCommand.Companion.add
import hm.binkley.layers.sets.LayerSetCommand.Companion.remove
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LayerSetRuleTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    @Test
    fun shouldAdd() {
        firstLayer.put("A", layerSet<ScratchLayer>(unlimited()))
            .saveAndNext(::ScratchLayer).put("A", add(firstLayer))
            .saveAndNext(::ScratchLayer)

        assertEquals(setOf(firstLayer), layers["A"])
    }

    @Test
    fun shouldRemove() {
        firstLayer.put("A", layerSet<ScratchLayer>(unlimited()))
            .saveAndNext(::ScratchLayer).put("A", add(firstLayer))
            .saveAndNext(::ScratchLayer).put("A", remove(firstLayer))
            .saveAndNext(::ScratchLayer)

        assertTrue(layers.getT<LayerSet<*>>("A").isEmpty())
    }
}
