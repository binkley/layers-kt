package hm.binkley.layers.x

import hm.binkley.layers.x.rules.XLatestOfRule.Companion.latestOfRule
import hm.binkley.layers.x.rules.XSumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLayersTest {
    @Test
    fun `should have a debuggable representation`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = sumOfRule()
        }

        layers.commitAndNext("AND third")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        "$layers" shouldBe """
0: XDefaultMutableLayer[AND zeroth]: {SALLY=<Rule>: Latest[default=0]}
1: XDefaultMutableLayer[AND first]: {SALLY=<Value[Int]>: 3}
2: XDefaultMutableLayer[AND second]: {SALLY=<Rule>: Sum}
3: XDefaultMutableLayer[AND third]: {SALLY=<Value[Int]>: 4}
        """.trimIndent()
    }

    @Test
    fun `should start fresh`() {
        val layers = XLayers(defaultMutableLayer = testMutableLayer)

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with new default layer`() {
        val layers = XLayers(defaultMutableLayer = testMutableLayer)
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with named layer`() {
        val layers = XLayers(defaultMutableLayer = testMutableLayer)
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
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 4)
    }

    @Test
    fun `should use latest rule`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = sumOfRule()
        }

        layers.commitAndNext("AND third")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 7)
    }

    @Test
    fun `should skip unassigned keys in layers for rules`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }

        // No changes for SALLY
        layers.commitAndNext("AND first")

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        layers shouldBe mapOf(testKey to 4)
    }

    @Test
    fun `should support what-if scenarios`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }
        val whatIf = layers.whatIf {
            this[testKey] = 1.toValue()
        }

        layers shouldBe mapOf(testKey to 0)
        whatIf shouldBe mapOf(testKey to 1)
    }

    @Test
    fun `should undo`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultMutableLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = sumOfRule()
        }

        layers.commitAndNext("AND first", testMutableLayer)
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers.rollback()

        layers shouldBe mapOf(testKey to 0)
    }
}
