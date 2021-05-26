package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import org.junit.jupiter.api.Test

internal class XLayerTest {
    @Test
    fun `should be self`() {
        open class TestBaseLayer<L : TestBaseLayer<L>> :
            XDefaultLayer<String, Int, L>("Test Layer") {
            fun foo() = self
        }

        class TestLayer : TestBaseLayer<TestLayer>() {
            fun bar() = this
        }

        // That this compiles given typing is part of the test:
        TestLayer().foo().bar() should beInstanceOf<TestLayer>()
    }

    @Test
    fun `should get latest value as requested type`() {
        val testKey = "SALLY"
        val layers = XLayers(
            "AND zeroth",
            defaultMutableLayer<String, Number>()
        )
        layers.edit {
            this[testKey] = layers.constantRule(testKey, 7)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.peek().getValueAs<Int>(testKey) shouldBe 3
    }
}
