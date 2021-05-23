package hm.binkley.layers.x

import hm.binkley.layers.x.rules.XLatestOfRule.Companion.latestOfRule
import hm.binkley.layers.x.rules.XSumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XLayersTest {
    @Test
    fun `should have a debuggable view`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
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
0: XDefaultMutableLayer[AND zeroth]: {SALLY=<Rule>: Latest(default=0)}
1: XDefaultMutableLayer[AND first]: {SALLY=<Value[Int]>: 3}
2: XDefaultMutableLayer[AND second]: {SALLY=<Rule>: Sum[Int]}
3: XDefaultMutableLayer[AND third]: {SALLY=<Value[Int]>: 4}
        """.trimIndent()
    }

    @Test
    fun `should start fresh`() {
        val layers = XLayers(defaultLayer = testMutableLayer)

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with new default layer`() {
        val layers = XLayers(defaultLayer = testMutableLayer)
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with named layer`() {
        val layers = XLayers(defaultLayer = testMutableLayer)
        layers.commitAndNext(::TestNamedLayer)

        layers.history shouldBe listOf<Map<String, XValueOrRule<Any>>>(
            mapOf(),
            mapOf(),
        )
    }

    @Test
    fun `should apply rule`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
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
            defaultLayer = testMutableLayer
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
            defaultLayer = testMutableLayer
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
            defaultLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = latestOfRule(0)
        }

        val whatIfA = layers.whatIf {
            this[testKey] = 1.toValue()
        }
        val whatIfB = layers.whatIf("<WHOZ-ZAT?>") {
            this[testKey] = 2.toValue()
        }

        layers shouldBe mapOf(testKey to 0)
        whatIfA shouldBe mapOf(testKey to 1)
        whatIfB shouldBe mapOf(testKey to 2)
    }

    @Test
    fun `should commit subtype layer`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = sumOfRule()
        }

        val layer = layers.commitAndNext("AND first", ::TestSubtypeLayer)
        layer.edit {
            this[testKey] = 1.toValue()
        }
        layer.foo(testKey)

        layers shouldBe mapOf(testKey to 2)
    }

    @Test
    fun `should undo`() {
        val testKey = "SALLY"
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
        )
        layers.edit {
            this[testKey] = sumOfRule()
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers.rollback()

        layers shouldBe mapOf(testKey to 0)
    }

    @Test
    fun `should create a constant rule`() {
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
        )
        val rule = layers.newRule("I AM CONSTANT") { -> 3 }

        val testKey = "SALLY"
        layers.edit {
            this[testKey] = rule
        }

        layers shouldBe mapOf(testKey to 3)
    }

    @Test
    fun `should create a values-based rule`() {
        val layers = XLayers(
            firstLayerName = "AND zeroth",
            defaultLayer = testMutableLayer
        )
        val rule = layers.newRule<Int>("I AM SUMMATION") { values ->
            values.sum()
        }

        val testKey = "SALLY"
        layers.edit {
            this[testKey] = rule
        }
        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers shouldBe mapOf(testKey to 1)
    }
}
