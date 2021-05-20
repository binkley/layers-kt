package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLayersTest {
    @Test
    fun `should start fresh`() {
        val layers = XLayers(defaultLayer = ::DefaultMutableLayer)

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue`() {
        val layers = XLayers(defaultLayer = ::DefaultMutableLayer)
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should have latest value`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultLayer = ::DefaultMutableLayer
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
    fun `should use all values`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND #1",
            defaultLayer = ::DefaultMutableLayer
        )
        layers.edit {
            this[testKey] = XSumOfRule()
        }

        layers.commitAndNext("AND #2")
        layers.edit {
            this[testKey] = XValue(3)
        }

        layers.commitAndNext("AND #3")
        layers.edit {
            this[testKey] = XValue(4)
        }

        layers shouldBe mapOf(testKey to 7)
    }
}

private class XLatestOfRule<V, T : V>(
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
