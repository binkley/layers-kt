package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers.RuleSurface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RuleTest {
    @Test
    fun shouldKnowName() {
        assertEquals("Bob", EgRule("Bob").name)
    }

    private class EgRule(name: String) : Rule<Any, Any>(name) {
        override fun invoke(layers: RuleSurface) = this
    }
}
