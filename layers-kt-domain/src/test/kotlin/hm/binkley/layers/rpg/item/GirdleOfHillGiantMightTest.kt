package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.newCharacter
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GirdleOfHillGiantMightTest {
    @Test
    fun `should have Hill Giant might`() {
        val character = newCharacter()
        character.commitAndNext(GirdleOfHillGiantMight())

        character[MIGHT.name] shouldBe 20
    }
}
