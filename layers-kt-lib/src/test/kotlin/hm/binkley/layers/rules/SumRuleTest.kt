package hm.binkley.layers.rules

import hm.binkley.layers.rules.SumRule.Companion.sumOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SumRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${SumRule()}" shouldBe "Rule: Sum"

    @Test
    fun `should calculate rule`() = sumOf(listOf(1, 2, 3)) shouldBe 6
}
