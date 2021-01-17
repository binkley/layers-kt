package hm.binkley.layers.rules

import hm.binkley.layers.rules.ConstantRule.Companion.constantRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${ConstantRule("bob", 0)}" shouldBe "<Rule>[bob]: Constant = 0"

    @Test
    fun `should calculate rule`() =
        constantRule("bob", 0)(listOf(1, 2, 3), emptyMap()) shouldBe 0
}
