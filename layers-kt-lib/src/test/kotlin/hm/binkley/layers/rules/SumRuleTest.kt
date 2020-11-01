package hm.binkley.layers.rules

import hm.binkley.layers.rules.SumRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SumRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${SumRule("bob", 0)}" shouldBe "<Rule>[bob]: Sum[Int]"

    @Test
    fun `should provide a default`() =
        sumOfRule("bob", 13)(listOf()) shouldBe 13

    @Test
    fun `should calculate rule`() =
        sumOfRule("bob", 0)(listOf(1, 2, 3)) shouldBe 6
}
