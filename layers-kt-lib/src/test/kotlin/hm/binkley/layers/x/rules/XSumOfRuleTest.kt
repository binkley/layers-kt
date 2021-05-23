package hm.binkley.layers.x.rules

import hm.binkley.layers.x.rules.XSumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XSumOfRuleTest {
    @Test
    fun `should have a debuggable view`() {
        "${sumOfRule()}" shouldBe "<Rule>: Sum[Int]"
    }
}
