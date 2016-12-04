package hm.binkley.layers.sets

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoveCommandTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpASets() {
        set = LayerSet(unlimited())
    }

    @Test
    fun shouldAdd() {
        set.add(firstLayer)
        RemoveCommand(firstLayer).invoke(set)

        assertTrue(set.isEmpty())
    }
}
