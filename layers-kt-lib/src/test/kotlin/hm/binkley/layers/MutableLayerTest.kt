package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableLayerTest {
    @Test
    fun `should edit blank layer`() {
        val layer = MutableLayer()
        layer.edit {
            this["bob"] = 3.asEntry
        }

        layer shouldBe mapOf("bob" to 3.asEntry)
    }

    @Test
    fun `should start layer with data`() {
        val layer = MutableLayer(mutableMapOf("bob" to 4.asEntry))

        layer shouldBe mapOf("bob" to 4.asEntry)
    }
}
