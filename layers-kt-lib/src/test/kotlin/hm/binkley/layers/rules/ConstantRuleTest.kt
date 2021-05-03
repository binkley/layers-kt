package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.ConstantRule.Companion.constantRule
import hm.binkley.layers.rules.ConstantRule.Companion.initConstantRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${ConstantRule("BOB", 10)}" shouldBe "<Rule>: Constant(constant=10)"

    @Test
    fun `should provide a default`() {
        constantRule("BOB", "apple")(
            "BOB",
            emptyList(),
            emptyMap(),
        ) shouldBe "apple"
    }

    @Test
    fun `should calculate rule`() =
        constantRule("BOB", 10)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 10

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initConstantRule("BOB", 10)

        key shouldBe "BOB"
        rule.defaultValue() shouldBe 10
    }
}
