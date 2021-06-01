package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import hm.binkley.layers.toValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatRuleTest {
    private val character = newCharacter()

    @Test
    fun `should have a debuggable presentation for stats`() =
        character.edit {
            "${statRule("FRED")}" shouldBe "<Rule>: Stat[Int](stat=FRED)"
        }

    @Test
    fun `should have a debuggable presentation for stat bonuses`() =
        character.edit {
            "${statBonusRule("WILMA")}" shouldBe "<Rule>: Stat-Bonus[Int](stat=WILMA)"
        }

    @Test
    fun `should calculate the bonus from a stat`() {
        val statKey = "BOXITUDE"
        val statBonusKey = "$statKey-BONUS"

        character.edit {
            this[statKey] = constantRule(12)
            this[statBonusKey] = statBonusRule(statKey)
        }

        character[statBonusKey] shouldBe 1
    }

    @Test
    fun `should round bonus from a stat`() {
        val statKey = "FRABNESS"
        val statBonusKey = "$statKey-BONUS"

        character.edit {
            this[statKey] = constantRule(13)
            this[statBonusKey] = statBonusRule(statKey)
        }

        character[statBonusKey] shouldBe 1
    }

    @Test
    fun `should require stats to be integers`() {
        val statKey = "DORKMENT"
        val statBonusKey = "$statKey-BONUS"

        character.edit {
            this[statKey] = constantRule("not an integer")
            this[statBonusKey] = statBonusRule(statKey)
        }

        shouldThrow<ClassCastException> {
            character[statBonusKey]
        }
    }

    @Test
    fun `should default stats to 0`() {
        val statKey = "HACKANCE"

        character.edit {
            this[statKey] = statRule(statKey)
        }

        character[statKey] shouldBe 0
    }

    @Test
    fun `should add up stat changes cumulatively`() {
        val statKey = "JOUSATION"

        character.edit {
            this[statKey] = statRule(statKey)
        }
        character.commitAndNext("Increase Jousation")
        character.edit {
            this[statKey] = 1.toValue()
        }

        character[statKey] shouldBe 1
    }
}
