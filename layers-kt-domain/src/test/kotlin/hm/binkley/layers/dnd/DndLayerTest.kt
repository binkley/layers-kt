package hm.binkley.layers.dnd

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class DndLayerTest {
    @Test
    fun `should test`() {
        DndLayer("<DOMAIN>", true).fakeForMutation.shouldBeTrue()
        DndLayer("<DOMAIN>", true) shouldBe mapOf()
    }

    @Test
    fun `should treat domains with different names as different`() {
        (DndLayer("A", true).equals(3)).shouldBeFalse()
        (DndLayer("A", true) == DndLayer("B", true)).shouldBeFalse()
        DndLayer("A", true).hashCode() shouldNotBe DndLayer(
            "B",
            true
        ).hashCode()
    }

    @Test
    fun `should treat domains with different fakeness as different`() {
        (DndLayer("<DOMAIN>", true) == DndLayer("<DOMAIN>", false))
            .shouldBeFalse()
        DndLayer("<DOMAIN>", true).hashCode() shouldNotBe
            DndLayer("<DOMAIN>", false).hashCode()
    }
}
