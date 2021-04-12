package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.LatestOfRule.Companion.initLatestOfRule
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyList
import java.util.Collections.emptyMap

internal class LatestOfRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${
        LatestOfRule(
            "bob",
            "?"
        )
        }" shouldBe "<Rule>[bob]: Latest(default=?)"

    @Test
    fun `should provide a default`() {
        latestOfRule("bob", "MISSING")(
            emptyList(), emptyMap()
        ) shouldBe "MISSING"
    }

    @Test
    fun `should calculate rule`() =
        latestOfRule("bob", 0)(listOf(1, 2, 3), emptyMap()) shouldBe 1

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initLatestOfRule("bob", "apple")

        key shouldBe "bob"
        rule.defaultValue() shouldBe "apple"
    }
}
