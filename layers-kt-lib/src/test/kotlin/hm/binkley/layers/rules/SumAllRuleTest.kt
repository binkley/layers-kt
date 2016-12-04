package hm.binkley.layers.rules

import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.ScratchLayer
import hm.binkley.layers.rules.Rule.Companion.sumAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SumAllRuleTest
    : LayersTestSupport<ScratchLayer>(::ScratchLayer) {
    @Test
    fun shouldSumAllValues() {
        firstLayer.
                put("A", sumAll()).
                saveAndNext(::ScratchLayer).
                put("A", 1).
                saveAndNext(::ScratchLayer).
                put("A", 2).
                saveAndNext(::ScratchLayer)

        assertEquals(3, layers["A"])
    }
}
