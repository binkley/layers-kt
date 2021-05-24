package hm.binkley.layers.x.rules

import hm.binkley.layers.x.rules.XLatestOfRule.Companion.latestOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLatestOfRuleTest {
    @Test
    fun `should have a debuggable view`() {
        "${latestOfRule("BOB", 31)}" shouldBe "<Rule/BOB>: Latest(default=31)"
    }
}
