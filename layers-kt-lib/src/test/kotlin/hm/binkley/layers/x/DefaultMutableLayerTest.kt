package hm.binkley.layers.x

import hm.binkley.layers.x.DefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DefaultMutableLayerTest {
    private val layer = defaultMutableLayer<String, Number>("TEST")

    @Test
    fun `should have a debuggable representation`() {
        "$layer" shouldBe "TEST: {}"
    }

    @Test
    fun `should edit`() {
        layer.edit {
            this["A KEY"] = 3.toValue()
        }

        layer shouldBe mapOf("A KEY" to 3.toValue())
    }

    @Test
    fun `should read other values independently in current layer`() {
        layer.edit {
            this["A KEY"] = 17.0.toValue()
            this["OTHER KEY"] = 3.toValue()

            val value = getOtherValueAs<Int>("OTHER KEY")

            value shouldBe 3
        }
    }
}
