package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import hm.binkley.layers.toValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GirdleOfHillGiantMightTest {
    @Test
    fun `should have Hill Giant might if better than yours`() {
        val character = newCharacter("TEST CHARACTER")
        character.commitAndNext("Bump might")
        character.edit {
            this[MIGHT.name] = 12.toValue() // 8+12 = 20
        }
        character.commitAndNext { GirdleOfHillGiantMight() }

        character[MIGHT.name] shouldBe 21
    }

    @Test
    fun `should have better than Hill Giant might if yours is better`() {
        val character = newCharacter("TEST CHARACTER")
        character.commitAndNext("Bump might")
        character.edit {
            this[MIGHT.name] = 14.toValue() // 8+14 = 22
        }
        character.commitAndNext { GirdleOfHillGiantMight() }

        character[MIGHT.name] shouldBe 22
    }
}
