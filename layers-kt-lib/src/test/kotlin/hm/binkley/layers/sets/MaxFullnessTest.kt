package hm.binkley.layers.sets

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.max
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MaxFullnessTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpSets() {
        set = LayerSet(max(1))
    }

    @Test
    fun shouldAdd() {
        set.add(firstLayer)

        assertEquals(setOf(firstLayer), set)
    }

    @Test
    fun shouldComplainWhenFull() {
        set.add(firstLayer)
        val secondLayer = firstLayer.saveAndNext(::ScratchLayer)

        assertThrows<Exception>(IndexOutOfBoundsException::class.java) {
            set.add(secondLayer)
        }
    }

    @Test
    fun shouldRemoveAndReadd() {
        with(set) {
            add(firstLayer)
            remove(firstLayer)
            add(firstLayer)
        }

        assertEquals(setOf(firstLayer), set)
    }
}
