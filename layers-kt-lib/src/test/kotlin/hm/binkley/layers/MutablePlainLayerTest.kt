package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutablePlainLayerTest {
    @Test
    fun `should edit blank layer`() {
        val layer = MutablePlainLayer("BOB")
        layer.edit {
            this["bob"] = 3.toValue()
        }

        layer shouldBe mapOf("bob" to 3.toValue())
    }

    @Test
    fun `should start layer with data`() {
        val layer = MutablePlainLayer(
            "BOB",
            mutableMapOf("bob" to 4.toValue())
        )

        layer shouldBe mapOf("bob" to 4.toValue())
    }
}
