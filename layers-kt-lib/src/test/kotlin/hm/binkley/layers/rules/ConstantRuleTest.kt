package hm.binkley.layers.rules

import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import hm.binkley.layers.TestEditMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable representation`() =
        "${TestEditMap().constantRule(3)}" shouldBe "<Rule>Constant(value=3)"

    @Test
    fun `should calculate`() {
        val rule = TestEditMap().constantRule(3)
        val value = rule(
            "A RULE",
            listOf(),
            defaultMutableLayers("TEST LAYERS")
        )

        value shouldBe 3
    }
}
