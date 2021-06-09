package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.util.stackOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CharacterTest {
    @Test
    fun `should start with a character and a stat layer`() {
        character("TEST CHARACTER").history.map {
            it::class
        } shouldBe stackOf(PlayerLayer::class, StatLayer::class)
    }
}
