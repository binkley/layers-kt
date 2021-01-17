package hm.binkley.layers.domain.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.domain.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule
import hm.binkley.layers.toEntry
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatBonusRuleTest {
    @Test
    fun `should calculate the bonus from a stat`() {
        // TODO: Use an enum for stats
        val statKey = "MIGHT"
        val statBonusKey = "$statKey-bonus"
        val layers = Layers.new {
            this[statKey] = latestOfRule(statKey, 8)
            this[statBonusKey] = statBonusRule(statBonusKey, statKey)
        }
        layers.saveAndNew(statKey) {
            this[statKey] = 12.toEntry()
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should round bonus from a stat`() {
        // TODO: Use an enum for stats
        val statKey = "MIGHT"
        val statBonusKey = "$statKey-bonus"
        val layers = Layers.new {
            this[statKey] = latestOfRule(statKey, 8)
            this[statBonusKey] = statBonusRule(statBonusKey, statKey)
        }
        layers.saveAndNew(statKey) {
            this[statKey] = 13.toEntry()
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should complain if dependency is not an integer`() {
        shouldThrowExactly<ClassCastException> {
            val statKey = "DORK"
            val statBonusKey = "$statKey-bonus"
            val layers = Layers.new {
                this[statKey] = latestOfRule("BOB", "not an integer")
                this[statBonusKey] = statBonusRule(statBonusKey, statKey)
            }
            layers.saveAndNew(statKey) {
                this[statKey] = "also not an integer".toEntry()
            }

            layers[statBonusKey] shouldBe 1
        }
    }

    @Test
    fun `should have a description`() {
        statBonusRule("BOB", "FRED").toString() shouldBe
            "<Rule>[BOB]: Bonus from FRED"
    }
}
