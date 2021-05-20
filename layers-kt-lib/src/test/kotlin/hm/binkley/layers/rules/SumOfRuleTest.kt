package hm.binkley.layers.rules

import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class SumOfRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${sumOfRule()}" shouldBe "<Rule>: Sum[Int]"

    @Test
    fun `should provide a default`() =
        sumOfRule()(
            "BOB",
            listOf(),
            emptyMap(),
        ) shouldBe 0

    @Test
    fun `should calculate rule`() =
        sumOfRule()(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 6
}
