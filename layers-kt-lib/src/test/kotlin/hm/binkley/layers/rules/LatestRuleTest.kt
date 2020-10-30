package hm.binkley.layers.rules

import hm.binkley.layers.rules.LatestRule.Companion.latestOf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LatestRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${LatestRule<Any>()}" shouldBe "Rule: Latest"

    @Test
    fun `should provide no default`() {
        shouldThrow<NoSuchElementException> {
            latestOf(listOf())
        }
    }

    @Test
    fun `should calculate rule`() = latestOf(listOf(1, 2, 3)) shouldBe 1
}
