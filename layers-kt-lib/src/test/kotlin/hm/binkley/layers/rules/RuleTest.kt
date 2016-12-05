package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RuleTest {
    @Test
    fun shouldKnowName() {
        assertEquals("Bob", EgRule("Bob").name)
    }

    private class EgRule(name: String) : Rule<Any>(name) {
        override fun invoke(layers: RuleSurface) = this
    }
}
