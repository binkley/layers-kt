package hm.binkley.layers.x

import hm.binkley.layers.x.DefaultMutableLayer.Companion.defaultMutableLayer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

private val simpleMutableLayer = defaultMutableLayer<String, Any>()

internal class XLayersTest {
    @Test
    fun `should have a debuggable representation`() {
        "${simpleMutableLayer("<INIT>")}" shouldBe "DefaultMutableLayer[<INIT>]: {}"
    }

    @Test
    fun `should start fresh`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should have layers`() {
        val layers = XLayers(defaultMutableLayer = simpleMutableLayer)
        layers.commitAndNext("BOB")

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
            this[testKey] = XValue(3)
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = XValue(4)
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
            this[testKey] = XValue(3)
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = XSumOfRule()
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = XValue(4)
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
            this[testKey] = XValue(4)
        }

        layers shouldBe mapOf(testKey to 4)
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