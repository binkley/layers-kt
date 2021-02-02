package hm.binkley.layers.dnd

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class DndTest {
    @Test
    fun `should test`() {
        Dnd("<DND>", true).fakeForMutation.shouldBeTrue()
        Dnd("<DND>", true) shouldBe mapOf()
    }

    @Test
    fun `should treat domains with different names as different`() {
        (Dnd("A", true).equals(3)).shouldBeFalse()
        (Dnd("A", true) == Dnd("B", true)).shouldBeFalse()
        Dnd("A", true).hashCode() shouldNotBe Dnd("B", true).hashCode()
    }

    @Test
    fun `should treat domains with different fakeness as different`() {
        (Dnd("<DND>", true) == Dnd("<DND>", false))
            .shouldBeFalse()
        Dnd("<DND>", true).hashCode() shouldNotBe
            Dnd("<DND>", false).hashCode()
    }
}
