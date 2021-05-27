package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer
import hm.binkley.layers.x.rules.XSumOfRule.Companion.sumOfRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XMutableLayersTest {
    val layers = XMutableLayers("AND zeroth", defaultMutableLayer<String, Any>())

    @Test
    fun `should have a debuggable view`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] = latestOfRule(testKey, 0)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = sumOfRule(testKey)
        }

        layers.commitAndNext("AND third")
        layers.edit {
            this[testKey] = 4.toValue()
        }

        "$layers" shouldBe """
0: XDefaultMutableLayer[AND zeroth]: {SALLY=<Rule/SALLY>: Latest(default=0)}
1: XDefaultMutableLayer[AND first]: {SALLY=<Value[Int]>: 3}
2: XDefaultMutableLayer[AND second]: {SALLY=<Rule/SALLY>: Sum[Int]}
3: XDefaultMutableLayer[AND third]: {SALLY=<Value[Int]>: 4}
        """.trimIndent()
    }

    @Test
    fun `should start fresh`() {
        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with new default layer`() {
        layers.commitAndNext("BOB")

        layers shouldBe emptyMap()
    }

    @Test
    fun `should commit and continue with named layer`() {
        layers.commitAndNext("AND first")

        layers.history shouldBe listOf<Map<String, XValueOrRule<Any>>>(
            mapOf(),
            mapOf(),
        )
    }

    @Test
    fun `should apply rule`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] = latestOfRule(testKey, 0)
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

        layers.edit {
            this[testKey] = latestOfRule(testKey, 0)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 3.toValue()
        }

        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = sumOfRule(testKey)
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

        layers.edit {
            this[testKey] = latestOfRule(testKey, 0)
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

        layers.edit {
            this[testKey] = latestOfRule(testKey, 0)
        }

        val whatIfA = layers.whatIf {
            this[testKey] = 1.toValue()
        }
        val whatIfB = layers.whatIf("<ONLY THE PHANTOM KNOWS>") {
            this[testKey] = 2.toValue()
        }

        layers shouldBe mapOf(testKey to 0)
        whatIfA shouldBe mapOf(testKey to 1)
        whatIfB shouldBe mapOf(testKey to 2)
    }

    @Test
    fun `should commit subtype layer`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] = sumOfRule(testKey)
        }

        val layer = layers.commitAndNext("AND first", ::TestSubtypeLayer)
        layer.edit {
            this[testKey] = 1.toValue()
        }
        layer.foo(testKey)

        layers shouldBe mapOf(testKey to 2)
    }

    @Test
    fun `should rollback`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] = sumOfRule(testKey)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers.rollback()

        layers shouldBe mapOf(testKey to 0)
    }

    @Test
    fun `should create a named constant rule`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] = newRule(testKey, "I AM CONSTANT") { -> 3 }
        }

        layers shouldBe mapOf(testKey to 3)
    }

    @Test
    fun `should create a values-based rule`() {
        val testKey = "SALLY"

        layers.edit {
            this[testKey] =
                newRule<Int>(testKey, "I AM SUMMATION") { values ->
                    values.sum()
                }
        }
        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers shouldBe mapOf(testKey to 1)
    }

    @Test
    fun `should create a layers-based rule`() {
        val testKey = "SALLY"
        val otherKey = "FRED"

        layers.edit {
            this[testKey] = newRule<Int>(
                testKey,
                "I AM COMPLICATED"
            ) { values, myLayers ->
                values.sum() + (myLayers[otherKey] as Int)
            }
            this[otherKey] = constantRule(otherKey, 3)
        }
        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 1.toValue()
        }

        layers shouldBe mapOf(testKey to 4, otherKey to 3)
    }

    @Test
    fun `should create a key-based rule`() {
        val testKey = "SALLY"
        layers.edit {
            this[testKey] = newRule<Int>(testKey, "KEYFULL") { key, _, _ ->
                key.length
            }
        }

        layers shouldBe mapOf(testKey to 5)
    }

    @Test
    fun `should create a default constant rule`() {
        val testKey = "SALLY"
        layers.edit {
            this[testKey] = constantRule(testKey, 17)
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = 34.toValue()
        }

        layers shouldBe mapOf(testKey to 17)
    }

    @Test
    fun `should create a latest-of rule`() {
        val testKey = "SALLY"
        layers.edit {
            this[testKey] = latestOfRule(testKey, "")
        }

        layers.commitAndNext("AND first")
        layers.edit {
            this[testKey] = "APPLE".toValue()
        }
        layers.commitAndNext("AND second")
        layers.edit {
            this[testKey] = "BANANA".toValue()
        }

        layers shouldBe mapOf(testKey to "BANANA")
    }
}

private class TestSubtypeLayer(
    name: String,
    editMap: () -> XEditMap<String, Any>,
) : XDefaultMutableLayer<String, Any, TestSubtypeLayer>(name, editMap) {
    @Suppress("UNCHECKED_CAST")
    fun foo(key: String) = edit {
        this[key] = (2 * getValueAs<Int>(key)).toValue()
    }
}