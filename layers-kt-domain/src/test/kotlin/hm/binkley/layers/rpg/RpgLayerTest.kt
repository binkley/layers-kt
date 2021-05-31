package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class RpgLayerTest {
    @Test
    fun `should treat domains with different names as different`() {
        CharacterLayer("A").equals(3).shouldBeFalse()
        (CharacterLayer("A") == CharacterLayer("B")).shouldBeFalse()
        CharacterLayer("A").hashCode() shouldNotBe CharacterLayer("B").hashCode()
    }

    @Test
    fun `should start a new character`() {
        val character = newCharacter("TEST CHARACTER")

        character["PLAYER-NAME"] shouldBe ""
        character["CHARACTER-NAME"] shouldBe ""
        character["MIGHT"] shouldBe 8
        character["MIGHT-BONUS"] shouldBe -1
    }
}
