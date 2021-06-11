package hm.binkley.layers

import hm.binkley.layers.DefaultMutableLayer.Companion.defaultMutableLayer
import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
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
    fun `should edit a value`() {
        val edited = layer.edit {
            this["A KEY"] = 3.toValue()
        }

        layer shouldBe mapOf("A KEY" to 3.toValue())
        edited should beTheSameInstanceAs(layer)
    }

    /** @todo Gross test */
    @Test
    fun `should edit a rule`() {
        val layers = defaultMutableLayers<String, Number>("TEST LAYERS")
        var rule: Rule<String, Number, Int>? = null
        val edited = layer.edit {
            rule = rule("TEST RULE") { key, values, l ->
                key shouldBe "A KEY"
                values shouldBe listOf(1, 2, 3)
                l shouldBe layers
                3
            }
            this["A KEY"] = rule!!
        }

        layer shouldBe mapOf("A KEY" to rule)
        edited should beTheSameInstanceAs(layer)

        rule!!("A KEY", listOf(1, 2, 3), layers) shouldBe 3
    }
}
