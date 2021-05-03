package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.DoubledRule.Companion.doubledRule
import hm.binkley.layers.rules.DoubledRule.Companion.initDoubledRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DoubledRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${DoubledRule("BOB", 2)}" shouldBe
            "<Rule>: Doubled[Int](default=4)"

    @Test
    fun `should provide a default`() {
        doubledRule("BOB", 13)(
            "BOB",
            emptyList(),
            emptyMap(),
        ) shouldBe 26
    }

    @Test
    fun `should calculate rule`() =
        doubledRule("BOB", 0)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 2

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initDoubledRule("BOB", 10)

        key shouldBe "BOB"
        rule.defaultValue("BOB") shouldBe 20
    }
}
