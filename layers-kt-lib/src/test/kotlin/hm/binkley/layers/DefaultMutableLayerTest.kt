package hm.binkley.layers

import hm.binkley.layers.DefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beTheSameInstanceAs
import org.junit.jupiter.api.Test

internal class DefaultMutableLayerTest {
    private val layer = defaultMutableLayer<String, Number>("TEST")

    @Test
    fun `should have a debuggable representation`() {
        "$layer" shouldBe "TEST: {}"
    }

    @Test
    fun `should edit`() {
        val edited = layer.edit {
            this["A KEY"] = 3.toValue()
        }

        layer shouldBe mapOf("A KEY" to 3.toValue())
        edited should beTheSameInstanceAs(layer)
    }
}
