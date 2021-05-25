package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLayerTest {
    @Test
    fun `should get value as requested type`() {
        val testKey = "SALLY"
        val mutableLayer = defaultMutableLayer<String, Number>()("BOB")
        mutableLayer.edit { this[testKey] = 3.toValue() }

        val layer: XLayer<String, Number> = mutableLayer

        layer.getValueAs<Int>(testKey) shouldBe 3
    }
}
