package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PlainLayerTest {
    @Test
    fun `should start layer blank`() {
        val layer = PlainLayer()

        layer shouldBe mapOf()
    }

    @Test
    fun `should start layer with data`() {
        val layer = PlainLayer(mutableMapOf("bob" to 4.toEntry()))

        layer shouldBe mapOf("bob" to 4.toEntry())
    }
}
