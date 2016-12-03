package hm.binkley.layers.sets

import hm.binkley.layers.Layers
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UnlimitedFullnessTest {
    lateinit var layers: Layers
    lateinit var firstLayer: ScratchLayer
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = Layers.firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
        set = LayerSet(unlimited())
    }

    @Test
    fun shouldAdd() {
        set.add(firstLayer)

        assertEquals(setOf(firstLayer), set)
    }

    @Test
    fun shouldRemove() {
        set.add(firstLayer)
        set.remove(firstLayer)

        assertTrue(set.isEmpty())
    }
}
