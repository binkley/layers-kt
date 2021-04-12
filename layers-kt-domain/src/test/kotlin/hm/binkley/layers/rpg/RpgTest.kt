package hm.binkley.layers.rpg

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class RpgTest {
    @Test
    fun `should test`() {
        Rpg("<RPG>", true).fakeForMutation.shouldBeTrue()
        Rpg("<RPG>", true) shouldBe mapOf()
    }

    @Test
    fun `should treat domains with different names as different`() {
        (Rpg("A", true).equals(3)).shouldBeFalse()
        (Rpg("A", true) == Rpg("B", true)).shouldBeFalse()
        Rpg("A", true).hashCode() shouldNotBe Rpg("B", true).hashCode()
    }

    @Test
    fun `should treat domains with different fakeness as different`() {
        (Rpg("<RPG>", true) == Rpg("<RPG>", false))
            .shouldBeFalse()
        Rpg("<RPG>", true).hashCode() shouldNotBe
            Rpg("<RPG>", false).hashCode()
    }
}
