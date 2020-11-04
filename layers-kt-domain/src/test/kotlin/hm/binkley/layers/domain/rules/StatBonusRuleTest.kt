package hm.binkley.layers.domain.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.rules.LatestOfRule
import hm.binkley.layers.toEntry
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatBonusRuleTest {
    @Test
    fun `should calculate the bonus from a stat`() {
        val statKey = "MIGHT"
        val statBonusKey = "$statKey-bonus"
        val layers = Layers.new {
            this[statKey] = LatestOfRule<Int>(statKey)
            this[statBonusKey] = StatBonusRule(statBonusKey, statKey)
        }
        layers.saveAndNew {
            this[statKey] = 13.toEntry()
        }

        layers[statBonusKey] shouldBe 1
    }
}
