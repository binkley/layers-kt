package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.rpg.items.GirdleOfHillGiantMight.Companion.girdleOfHillGiantMight
import hm.binkley.layers.toValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GirdleOfHillGiantMightTest {
    @Test
    fun `should start inactive`() {
        val character = character("TEST CHARACTER")
        character.commitAndNext("Set might")
        character.edit {
            this[MIGHT.name] = 18.toValue()
        }

        character.commitAndNext { girdleOfHillGiantMight() }

        character[MIGHT.name] shouldBe 18
    }

    @Test
    fun `should have Hill Giant might if better than existing`() {
        val character = character("TEST CHARACTER")
        character.commitAndNext("Set might")
        character.edit {
            this[MIGHT.name] = 18.toValue()
        }

        character.commitAndNext { girdleOfHillGiantMight().don() }

        character[MIGHT.name] shouldBe 19
    }

    @Test
    fun `should have better than Hill Giant might if existing is better`() {
        val character = character("TEST CHARACTER")
        character.commitAndNext("Set might")
        character.edit {
            this[MIGHT.name] = 20.toValue()
        }

        character.commitAndNext { girdleOfHillGiantMight().don() }

        character[MIGHT.name] shouldBe 20
    }
}
