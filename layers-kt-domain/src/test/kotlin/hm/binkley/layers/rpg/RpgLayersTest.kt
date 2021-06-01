package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import hm.binkley.layers.util.stackOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RpgLayersTest {
    @Test
    fun `should start with a character and a stat layer`() {
        newCharacter().history.map {
            it::class
        } shouldBe stackOf(CharacterLayer::class, StatLayer::class)
    }
}
