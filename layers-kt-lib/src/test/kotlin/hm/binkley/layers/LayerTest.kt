package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LayerTest {
    @Test
    fun `should start layer blank`() {
        val layer = Layer()

        layer shouldBe mapOf()
    }

    @Test
    fun `should start layer with data`() {
        val layer = Layer(mutableMapOf("bob" to 4))

        layer shouldBe mapOf("bob" to 4)
    }
}
