package hm.binkley.layers.x.rules

import hm.binkley.layers.x.rules.XLatestOfRule.Companion.latestOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLatestOfRuleTest {
    @Test
    fun `should have a debuggable view`() {
        "${latestOfRule(31)}" shouldBe "<Rule>: Latest(default=31)"
    }
}
