package hm.binkley.layers.rules

import hm.binkley.layers.rules.LatestRule.Companion.latestRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyList
import java.util.Collections.emptyMap

internal class LatestRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${
        LatestRule(
            "BOB",
            "?"
        )
        }" shouldBe "<Rule>: Latest(default=?)"

    @Test
    fun `should provide a default`() {
        latestRule("BOB", "MISSING")(
            "BOB",
            emptyList(),
            emptyMap(),
        ) shouldBe "MISSING"
    }

    @Test
    fun `should calculate rule`() =
        latestRule("BOB", 0)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 1
}
