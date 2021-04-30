package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.RpgLayer.Companion.newCharacter
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class RpgLayerTest {
    @Test
    fun `should treat domains with different names as different`() {
        RpgLayer("A").equals(3).shouldBeFalse()
        (RpgLayer("A") == RpgLayer("B")).shouldBeFalse()
        RpgLayer("A").hashCode() shouldNotBe RpgLayer("B").hashCode()
    }

    @Test
    fun `should start a new character`() {
        val character = newCharacter()
        character["PLAYER-NAME"] shouldBe ""
        character["CHARACTER-NAME"] shouldBe ""
        character["MIGHT"] shouldBe 8
        character["MIGHT-BONUS"] shouldBe -1
    }
}
