package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import hm.binkley.layers.toValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatRuleTest {
    private val layers = newCharacter("TEST CHARACTER")

    @Test
    fun `should have a debuggable presentation`() =
        "${StatBonusRule("FRED")}" shouldBe
            "<Rule>: Stat-Bonus[Int](stat=FRED)"

    @Test
    fun `should calculate the bonus from a stat`() {
        val statKey = "BOXITUDE"
        val statBonusKey = "$statKey-BONUS"

        layers.edit {
            this[statKey] = constantRule(12)
            this[statBonusKey] = statBonusRule(statKey)
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should round bonus from a stat`() {
        val statKey = "FRABNESS"
        val statBonusKey = "$statKey-BONUS"

        layers.edit {
            this[statKey] = constantRule(13)
            this[statBonusKey] = statBonusRule(statKey)
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should require stats to be integers`() {
        val statKey = "DORKMENT"
        val statBonusKey = "$statKey-BONUS"

        layers.edit {
            this[statKey] = constantRule("not an integer")
            this[statBonusKey] = statBonusRule(statKey)
        }

        shouldThrow<ClassCastException> {
            layers[statBonusKey]
        }
    }

    @Test
    fun `should default stats to 8`() {
        val statKey = "HACKANCE"

        layers.edit {
            this[statKey] = statRule(statKey)
        }

        layers[statKey] shouldBe 8
    }

    @Test
    fun `should add up stat changes cumulatively`() {
        val statKey = "JOUSATION"

        layers.edit {
            this[statKey] = statRule(statKey)
        }
        layers.commitAndNext("Increase Jousation")
        layers.edit {
            this[statKey] = 1.toValue()
        }

        layers[statKey] shouldBe 9
    }
}
