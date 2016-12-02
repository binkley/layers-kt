package hm.binkley.layers.sets

import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

internal class LayerSetTest {
    @Test
    fun shouldKnowName() {
        assertEquals("Bob", LayerSet<ScratchLayer>("Bob", unlimited()).name)
    }

    @Test
    fun shouldComplainWhenFull() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)

        assertThrows<Exception>(IndexOutOfBoundsException::class.java) {
            LayerSet("Die add", AlwaysFull()).add(firstLayer)
        }
    }

    @Test
    fun shouldComplainWhenEmpty() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)

        assertThrows<Exception>(NoSuchElementException::class.java) {
            LayerSet("Die remove", AlwaysFull()).remove(firstLayer)
        }
    }

    class AlwaysFull : FullnessFunction<ScratchLayer>("Always full") {
        override fun invoke(set: LayerSet<ScratchLayer>, layer: ScratchLayer): Boolean = true
    }
}
