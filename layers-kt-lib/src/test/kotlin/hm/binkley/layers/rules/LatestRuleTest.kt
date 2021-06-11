package hm.binkley.layers.rules

import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import hm.binkley.layers.Rule
import hm.binkley.layers.TestEditMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LatestRuleTest {
    @Test
    fun `should have a debuggable representation`() =
        "${TestEditMap().latestRule(7)}" shouldBe "<Rule>Latest(default=7)"

    @Test
    fun `should calculate`() {
        val rule: Rule<String, Number, Int> = TestEditMap().latestRule(7)
        val value = rule(
            "A RULE",
            listOf(1, 2, 3),
            defaultMutableLayers("TEST LAYERS")
        )

        value shouldBe 3
    }

    @Test
    fun `should provide a default value`() {
        val rule: Rule<String, Number, Int> = TestEditMap().latestRule(7)
        val value = rule(
            "A RULE",
            emptyList(),
            defaultMutableLayers("TEST LAYERS")
        )

        value shouldBe 7
    }
}
