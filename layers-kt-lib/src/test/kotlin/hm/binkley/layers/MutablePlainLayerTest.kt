package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutablePlainLayerTest {
    @Test
    fun `should be very mappish`() {
        val layer = MutablePlainLayer("BOB")
        // Passing this test really means these funs/props compile
        layer.clear()
        layer.entries
        layer.putAll(mapOf(bobKey to 4.toValue()))
        layer.remove(bobKey)
        layer[bobKey] = 3.toValue()
        layer.values

        layer shouldBe mapOf(bobKey to 3.toValue())
    }

    @Test
    fun `should edit blank layer`() {
        val layer = MutablePlainLayer("BOB")
        layer.edit {
            this[bobKey] = 3.toValue()
        }

        layer shouldBe mapOf(bobKey to 3.toValue())
    }

    @Test
    fun `should start layer with data`() {
        val layer = MutablePlainLayer(
            "BOB",
            mutableMapOf(bobKey to 4.toValue())
        )

        layer shouldBe mapOf(bobKey to 4.toValue())
    }
}

private const val bobKey = "bob"
