package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.DoubledRule.Companion.doubledRule
import hm.binkley.layers.rules.DoubledRule.Companion.initDoubledRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DoubledRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${DoubledRule("bob", 2)}" shouldBe "<Rule>[bob]: Doubled(default=4)"

    @Test
    fun `should provide a default`() {
        doubledRule("bob", 13)(emptyList(), emptyMap()) shouldBe 26
    }

    @Test
    fun `should calculate rule`() =
        doubledRule("bob", 0)(listOf(1, 2, 3), emptyMap()) shouldBe 2

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initDoubledRule("bob", 10)

        key shouldBe "bob"
        rule.defaultValue() shouldBe 20
    }
}
