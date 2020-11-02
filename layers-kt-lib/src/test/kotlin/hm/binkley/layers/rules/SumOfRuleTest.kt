package hm.binkley.layers.rules

import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class SumOfRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${SumOfRule("bob", 0)}" shouldBe "<Rule>[bob]: Sum[Int]"

    @Test
    fun `should provide a default`() =
        sumOfRule("bob", 13)(listOf(), emptyMap()) shouldBe 13

    @Test
    fun `should calculate rule`() =
        sumOfRule("bob", 0)(listOf(1, 2, 3), emptyMap()) shouldBe 6
}
