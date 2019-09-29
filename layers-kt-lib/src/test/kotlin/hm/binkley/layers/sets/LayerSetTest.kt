package hm.binkley.layers.sets

import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.ScratchLayer
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

internal class LayerSetTest {
    @Test
    fun shouldComplainWhenFull() {
        val (_, firstLayer) = firstLayer(::ScratchLayer)

        assertThrows(IndexOutOfBoundsException::class.java) {
            LayerSet(AlwaysFull()).add(firstLayer)
        }
    }

    @Test
    fun shouldComplainWhenEmpty() {
        val (_, firstLayer) = firstLayer(::ScratchLayer)

        assertThrows(NoSuchElementException::class.java) {
            LayerSet(AlwaysFull()).remove(firstLayer)
        }
    }

    class AlwaysFull : FullnessFunction<ScratchLayer>("Always full") {
        override fun invoke(set: LayerSet<ScratchLayer>,
                layer: ScratchLayer): Boolean = true
    }
}
