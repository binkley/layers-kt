package hm.binkley.layers.sets

import hm.binkley.layers.Layers
import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.sets.FullnessFunction.Companion.unlimited
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RemoveCommandTest {
    lateinit var layers: Layers
    lateinit var firstLayer: ScratchLayer
    lateinit var set: LayerSet<ScratchLayer>

    @BeforeEach
    fun setUpLayers() {
        val (layers, firstLayer) = firstLayer(::ScratchLayer)
        this.layers = layers
        this.firstLayer = firstLayer
        set = LayerSet(unlimited())
    }

    @Test
    fun shouldAdd() {
        set.add(firstLayer)
        RemoveCommand(firstLayer).invoke(set)

        assertTrue(set.isEmpty())
    }
}
