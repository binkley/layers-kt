package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

private val simpleMutableLayer = defaultMutableLayer<String, Any>()

internal class XLayersTest {
    @Test
    fun `should have debuggable representation`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultMutableLayer = simpleMutableLayer
        )
        layers.edit {
            this[testKey] = XLatestOfRule(0)
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = XSumOfRule()
        }

        layers.commitAndNext("AND #4")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        "$layers" shouldBe """
0: XDefaultMutableLayer[AND #4]: {SALLY=<Value[Int]>: 4}
1: XDefaultMutableLayer[AND #3]: {SALLY=<Rule>: Sum}
2: XDefaultMutableLayer[AND #2]: {SALLY=<Value[Int]>: 3}
3: XDefaultMutableLayer[AND #1]: {SALLY=<Rule>: Latest[default=0]}
        """.trimIndent()
    }

    /** @todo Move to a test for layer, not layers */
    @Test
    fun `should have a debuggable representation`() {
        "${simpleMutableLayer("<INIT>")}" shouldBe "XDefaultMutableLayer[<INIT>]: {}"
    }

    @Test
    fun `should start fresh`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with new default layer`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with named layer`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)
        layers.commitAndNext(::TestNamedLayer)

        layers.layers shouldBe listOf<Map<String, XValueOrRule<Any>>>(
            mapOf(),
            mapOf(),
        )
    }

    @Test
    fun `should apply rule`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultMutableLayer = simpleMutableLayer
        )
        layers.edit {
            this[testKey] = XLatestOfRule(0)
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 4)
    }

    @Test
    fun `should use latest rule`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultMutableLayer = simpleMutableLayer
        )
        layers.edit {
            this[testKey] = XLatestOfRule(0)
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = XSumOfRule()
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 7)
    }

    @Test
    fun `should skip unassigned keys in layers for rules`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultMutableLayer = simpleMutableLayer
        )
        layers.edit {
            this[testKey] = XLatestOfRule(0)
        }

        // No changes for SALLY
        layers.commitAndNext("AND #2")

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 4)
    }

    @Test
    fun `should support what-if scenarios`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultMutableLayer = simpleMutableLayer
        )
        layers.edit {
            this[testKey] = XLatestOfRule(0)
        }
        val whatIf = layers.whatIf {
            this[testKey] = 1.toValue()
        }

        layers shouldBe mapOf(testKey to 0)
        whatIf shouldBe mapOf(testKey to 1)
    }
}

private class XLatestOfRule<V : Any, T : V>(
    private val default: T,
) : XRule<V, T>("Latest[default=$default]") {
    override fun invoke(
        values: List<T>,
        layers: XLayers<*, V, *>,
    ) = values.firstOrNull() ?: default
}

private class XSumOfRule : XRule<Any, Int>("Sum") {
    override fun invoke(
        values: List<Int>,
        layers: XLayers<*, Any, *>,
    ) = values.sum()
}

private class TestNamedLayer :
    XDefaultMutableLayer<String, Any, TestNamedLayer>("FRED")
