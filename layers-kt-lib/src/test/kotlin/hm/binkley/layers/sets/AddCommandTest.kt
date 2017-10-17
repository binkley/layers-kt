package hm.binkley.layers.sets

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddCommandTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    private lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpSets() {
        set = LayerSet(unlimited())
    }

    @Test
    fun shouldAdd() {
        AddCommand(firstLayer).invoke(set)

        assertEquals(setOf(firstLayer), set)
    }
}
