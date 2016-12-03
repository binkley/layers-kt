package hm.binkley.layers.sets

import hm.binkley.layers.ScratchLayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LayerSetCommandTest {
    @Test
    fun shouldKnowName() {
        assertEquals("Bob", object : LayerSetCommand<ScratchLayer>("Bob") {
            override fun invoke(set: LayerSet<ScratchLayer>) {
            }
        }.name)
    }
}
