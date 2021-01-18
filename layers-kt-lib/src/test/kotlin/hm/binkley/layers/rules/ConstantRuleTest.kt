package hm.binkley.layers.rules

import hm.binkley.layers.rules.ConstantRule.Companion.constantRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class ConstantRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${ConstantRule("bob", 10)}" shouldBe "<Rule>[bob]: Constant: 10"

    @Test
    fun `should calculate rule`() =
        constantRule("bob", 10)(listOf(1, 2, 3), emptyMap()) shouldBe 10
}
