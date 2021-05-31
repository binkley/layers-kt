package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test

class RpgLayersTest {
    private val character = newCharacter("TEST CHARACTER")

    @Test
    fun `should start with a character layer`() {
        character.current.shouldBeInstanceOf<CharacterLayer>()
    }
}
