package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.ConstantRule.Companion.constantRule
import hm.binkley.layers.rules.ConstantRule.Companion.initConstantRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${ConstantRule("bob", 10)}" shouldBe
            "<Rule>[bob]: Constant(constant=10)"

    @Test
    fun `should provide a default`() {
        constantRule("bob", "apple")(emptyList(), emptyMap()) shouldBe "apple"
    }

    @Test
    fun `should calculate rule`() =
        constantRule("bob", 10)(listOf(1, 2, 3), emptyMap()) shouldBe 10

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initConstantRule("bob", 10)

        key shouldBe "bob"
        rule.defaultValue() shouldBe 10
    }
}
