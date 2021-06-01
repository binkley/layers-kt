package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.toValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GirdleOfHillGiantMightTest {
    @Test
    fun `should have Hill Giant might if better than yours`() {
        val character = newCharacter()
        character.commitAndNext("Bump might")
        character.edit {
            this[MIGHT.name] = 18.toValue()
        }
        character.commitAndNext { GirdleOfHillGiantMight() }

        character[MIGHT.name] shouldBe 19
    }

    @Test
    fun `should have better than Hill Giant might if yours is better`() {
        val character = newCharacter()
        character.commitAndNext("Bump might")
        character.edit {
            this[MIGHT.name] = 20.toValue()
        }
        character.commitAndNext { GirdleOfHillGiantMight() }

        character[MIGHT.name] shouldBe 20
    }
}
