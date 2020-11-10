package hm.binkley.layers

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class PlainLayerTest {
    @Test
    fun `should start layer blank`() {
        val layer = PlainLayer("<INIT>")

        layer shouldBe mapOf()
    }

    @Test
    fun `should start layer with data`() {
        val layer = PlainLayer("<INIT>", mutableMapOf("bob" to 4.toEntry()))

        layer shouldBe mapOf("bob" to 4.toEntry())
    }

    @Test
    fun `should treat layers with different names as different`() {
        (PlainLayer("A") == PlainLayer("B")) shouldBe false
        PlainLayer("A").hashCode() shouldNotBe PlainLayer("B").hashCode()
    }
}
