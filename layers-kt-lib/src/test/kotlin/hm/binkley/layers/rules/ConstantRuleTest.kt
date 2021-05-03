package hm.binkley.layers.rules

import hm.binkley.layers.rules.ConstantRule.Companion.constantRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${ConstantRule(10)}" shouldBe "<Rule>: Constant(constant=10)"

    @Test
    fun `should provide a default`() {
        constantRule("apple")(
            "BOB",
            emptyList(),
            emptyMap(),
        ) shouldBe "apple"
    }

    @Test
    fun `should calculate rule`() =
        constantRule(10)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 10
}
