package hm.binkley.layers.rules

import hm.binkley.layers.rules.LatestRule.Companion.latestOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyList

internal class LatestRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${LatestRule("bob", "")}" shouldBe "<Rule>[bob]: Latest"

    @Test
    fun `should provide a default`() {
        latestOfRule("bob", "MISSING")(emptyList()) shouldBe "MISSING"
    }

    @Test
    fun `should calculate rule`() =
        latestOfRule("bob", 0)(listOf(1, 2, 3)) shouldBe 1
}
