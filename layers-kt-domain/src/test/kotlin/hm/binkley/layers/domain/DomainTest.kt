package hm.binkley.layers.domain

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class DomainTest {
    @Test
    fun `should test`() {
        Domain("<DOMAIN>", true).fakeForMutation.shouldBeTrue()
        Domain("<DOMAIN>", true) shouldBe mapOf()
    }

    @Test
    fun `should treat domains with different names as different`() {
        (Domain("A", true).equals(3)).shouldBeFalse()
        (Domain("A", true) == Domain("B", true)).shouldBeFalse()
        Domain("A", true).hashCode() shouldNotBe Domain("B", true).hashCode()
    }

    @Test
    fun `should treat domains with different fakeness as different`() {
        (Domain("<DOMAIN>", true) == Domain("<DOMAIN>", false))
            .shouldBeFalse()
        Domain("<DOMAIN>", true).hashCode() shouldNotBe
            Domain("<DOMAIN>", false).hashCode()
    }
}
