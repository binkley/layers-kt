package hm.binkley.layers

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LayerTest {
    @Test
    fun `should be a decent object`() {
        val layerA = Layer("<INIT>")
        val layerB = Layer("<INIT>")

        // Have to call equals directly to avoid Kotest short-circuiting
        (layerA == layerB).shouldBeTrue()
        val nameChanged = Layer("<NAME MATTERS>")
        (nameChanged == layerB).shouldBeFalse()
        val mapChanged = Layer(
            "<MAP MATTERS>",
            mutableMapOf("bob" to 4.toValue())
        )
        (mapChanged == layerB).shouldBeFalse()

        layerA.hashCode() shouldBe layerB.hashCode()
    }

    @Test
    fun `should start layer blank`() {
        val layer = Layer("<INIT>")

        layer shouldBe mapOf()
    }

    @Test
    fun `should start layer with data`() {
        val layer = Layer("<INIT>", mutableMapOf("bob" to 4.toValue()))

        layer shouldBe mapOf("bob" to 4.toValue())
    }
}
