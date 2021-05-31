package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
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
        val statKey = "FRUBNESS"
        val statBonusKey = "$statKey-BONUS"

        layers.edit {
            this[statKey] = constantRule(13)
            this[statBonusKey] = statBonusRule(statKey)
        }

        layers[statBonusKey] shouldBe 1
    }

    @Test
    fun `should complain if dependency is not an integer`() {
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
}
