package hm.binkley.layers.rpg.rules

import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.rules.StatBonusRule.Companion.statBonusRule
import hm.binkley.layers.rules.LatestRule.Companion.latestRule
import hm.binkley.layers.toValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${statBonusRule("FRED")}" shouldBe
            "<Rule>: Stat-Bonus[Int](stat=FRED)"

    @Test
    fun `should calculate the bonus from a stat`() {
        val statKey = "BOXITUDE"
        val statBonusKey = "$statKey-BONUS"
        val layers = Layers.new {
            this[statKey] = latestRule(statKey, 8)
            this[statBonusKey] = statBonusRule(statKey)
        }
        layers.commitAndNext(statKey) {
            this[statKey] = 12.toValue()
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should round bonus from a stat`() {
        val statKey = "FRUBNESS"
        val statBonusKey = "$statKey-BONUS"
        val layers = Layers.new {
            installStat(statKey)
        }
        layers.commitAndNext(statKey) {
            this[statKey] = 13.toValue()
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should complain if dependency is not an integer`() {
        shouldThrow<ClassCastException> {
            val statKey = "DORKMENT"
            val statBonusKey = "$statKey-BONUS"
            val layers = Layers.new {
                this[statKey] = latestRule("BOB", "not an integer")
                this[statBonusKey] = statBonusRule(statKey)
            }

            layers[statBonusKey]
        }
    }
}
