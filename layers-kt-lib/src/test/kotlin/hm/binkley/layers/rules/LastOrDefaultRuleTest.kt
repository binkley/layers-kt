package hm.binkley.layers.rules

import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import hm.binkley.layers.Rule
import hm.binkley.layers.TestEditMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LastOrDefaultRuleTest {
    @Test
    fun `should have a debuggable representation`() =
        "${TestEditMap().lastOrDefaultRule(7)}" shouldBe "<Rule>Latest(default=7)"

    @Test
    fun `should calculate`() {
        val rule: Rule<String, Number, Int> = TestEditMap().lastOrDefaultRule(7)
        val value = rule(
            "A RULE",
            sequenceOf(1, 2, 3),
            defaultMutableLayers("TEST LAYERS")
        )

        value shouldBe 3
    }

    @Test
    fun `should provide a default value`() {
        val rule: Rule<String, Number, Int> = TestEditMap().lastOrDefaultRule(7)
        val value = rule(
            "A RULE",
            emptySequence(),
            defaultMutableLayers("TEST LAYERS")
        )

        value shouldBe 7
    }
}
