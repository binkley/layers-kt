package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.RpgLayers.Companion.newCharacter
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RpgLayersTest {
    private val character = newCharacter("TEST CHARACTER")

    @Test
    fun `should have might`() {
        character["MIGHT"] shouldBe 8
        character["MIGHT-BONUS"] shouldBe -1
    }

    @Test
    fun `should have deftness`() {
        character["DEFTNESS"] shouldBe 8
        character["DEFTNESS-BONUS"] shouldBe -1
    }

    @Test
    fun `should have grit`() {
        character["GRIT"] shouldBe 8
        character["GRIT-BONUS"] shouldBe -1
    }

    @Test
    fun `should have wit`() {
        character["WIT"] shouldBe 8
        character["WIT-BONUS"] shouldBe -1
    }

    @Test
    fun `should have foresight`() {
        character["FORESIGHT"] shouldBe 8
        character["FORESIGHT-BONUS"] shouldBe -1
    }

    @Test
    fun `should have presence`() {
        character["PRESENCE"] shouldBe 8
        character["PRESENCE-BONUS"] shouldBe -1
    }
}
