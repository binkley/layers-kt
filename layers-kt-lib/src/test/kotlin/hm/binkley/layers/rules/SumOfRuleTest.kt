package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.SumOfRule.Companion.initSumOfRule
import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class SumOfRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${SumOfRule(0)}" shouldBe "<Rule>: Sum[Int]"

    @Test
    fun `should provide a default`() =
        sumOfRule(13)(
            "BOB",
            listOf(),
            emptyMap(),
        ) shouldBe 13

    @Test
    fun `should calculate rule`() =
        sumOfRule(0)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 6

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initSumOfRule("BOB", 10)

        key shouldBe "BOB"
        rule.defaultValue("BOB") shouldBe 10
    }
}
