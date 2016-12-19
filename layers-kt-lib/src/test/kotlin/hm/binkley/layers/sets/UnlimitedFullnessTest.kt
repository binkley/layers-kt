package hm.binkley.layers.sets

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UnlimitedFullnessTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpSets() {
        set = LayerSet(unlimited())
    }

    @Test
    fun shouldAdd() {
        set.add(firstLayer)

        assertEquals(setOf(firstLayer), set)
    }

    @Test
    fun shouldRemove() {
        with(set) {
            add(firstLayer)
            remove(firstLayer)
        }

        assertTrue(set.isEmpty())
    }
}
