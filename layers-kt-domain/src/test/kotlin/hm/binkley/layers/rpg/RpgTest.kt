package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.Rpg.Companion.newCharacter
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class RpgTest {
    @Test
    fun `should treat domains with different names as different`() {
        Rpg("A").equals(3).shouldBeFalse()
        (Rpg("A") == Rpg("B")).shouldBeFalse()
        Rpg("A").hashCode() shouldNotBe Rpg("B").hashCode()
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
