package hm.binkley.layers.x

import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import org.junit.jupiter.api.Test

internal class LayerTest {
    @Test
    fun `should know oneself`() {
        val layer: Layer<String, Number, *> = TestLayer()

        layer.self should beInstanceOf<TestLayer>()
    }
}

private class TestLayer(
    map: Map<String, ValueOrRule<Number>> = mapOf(),
) : Layer<String, Number, TestLayer>,
    Map<String, ValueOrRule<Number>> by map
