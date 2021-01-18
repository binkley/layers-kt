package hm.binkley.layers.rules

import hm.binkley.layers.rules.DoubledRule.Companion.doubledRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyList
import java.util.Collections.emptyMap

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
}
