package hm.binkley.layers.x

import hm.binkley.layers.x.DefaultMutableLayers.Companion.defaultMutableLayers
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DefaultMutableLayersTest {
    private val defaultLayers = defaultMutableLayers<String, Number>("A NAME")

    // TODO: Less cumbersome syntax for custom layers
    private val customLayers =
        DefaultMutableLayers<String, Number, DefaultMutableLayer<String, Number, *>>(
            "Test Custom Layers"
        ) { DefaultMutableLayer(it) }

    @Test
    fun `should have a debuggable representation`() {
        "$defaultLayers" shouldBe """
A NAME: {}
0: <INIT>: {}
        """.trimIndent()
    }

    @Test
    fun `should be named`() {
        defaultLayers.name shouldBe "A NAME"
    }

    @Test
    fun `should be a computed map`() {
        defaultLayers.edit {
            this["A KEY"] = latestOfRule(7)
        }
        defaultLayers.commitAndNext("First").edit {
            this["A KEY"] = 3.toValue()
        }

        defaultLayers shouldBe mapOf("A KEY" to 3)
    }

    @Test
    fun `should gather view entries from layers to execute a rule`() {
        // TODO: This test feels upside down
        val rule = object : Rule<String, Number, Int>("A RULE") {
            override fun invoke(
                key: String,
                values: List<Int>,
                view: Map<String, Number>,
            ): Int {
                view shouldBe emptyMap()
                return 3
            }
        }

        defaultLayers.edit {
            this["A KEY"] = rule
        }

        defaultLayers["A KEY"] shouldBe 3
    }

    @Test
    fun `should read other values in layers`() {
        defaultLayers.edit {
            this["OTHER KEY"] = constantRule(3)

            val value = getOtherValueAs<Int>("OTHER KEY")

            value shouldBe 3
        }
    }

    @Test
    fun `should run what-if scenarios`() {
        val map = defaultLayers.whatIf {
            this["A KEY"] = constantRule(3)
        }

        map shouldBe mapOf("A KEY" to 3)
        defaultLayers shouldBe emptyMap()
    }

    @Test
    fun `should add a sub-type layer and use it`() {
        val layer = customLayers.commitAndNext { TestCustomMutableLayer() }

        // That this compiles *is* the test
        layer.foo()
    }
}

private class TestCustomMutableLayer :
    DefaultMutableLayer<String, Number, TestCustomMutableLayer>("TEST") {
    fun foo() = Unit
}
