package hm.binkley.layers

import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutablePlainLayerTest {
    @Test
    fun `should be very mappish`() {
        val layer = DefaultMutableLayer("BOB")
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
    fun `should construct from existing data`() {
        val layer = DefaultMutableLayer("BOB", mapOf("SALLY" to sumOfRule()))

        layer shouldBe mapOf("SALLY" to 0)
    }

    @Test
    fun `should edit blank layer`() {
        val layer = DefaultMutableLayer("BOB")
        layer.edit {
            this[bobKey] = 3.toValue()
        }

        layer shouldBe mapOf(bobKey to 3.toValue())
    }

    @Test
    fun `should start layer with data`() {
        val layer = DefaultMutableLayer(
            "BOB",
            mapOf(bobKey to 4.toValue())
        )

        layer shouldBe mapOf(bobKey to 4.toValue())
    }
}

private const val bobKey = "bob"
