package hm.binkley.layers.sets

import hm.binkley.layers.Layers
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.max
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MaxFullnessTest {
    lateinit var layers: Layers
    lateinit var firstLayer: ScratchLayer
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = Layers.firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
        set = LayerSet("Bob", max(1))
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
        set.add(firstLayer)
        set.remove(firstLayer)
        set.add(firstLayer)

        assertEquals(setOf(firstLayer), set)
    }
}
