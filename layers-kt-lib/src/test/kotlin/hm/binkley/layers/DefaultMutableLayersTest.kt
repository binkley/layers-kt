package hm.binkley.layers

import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DefaultMutableLayersTest {
    private val defaultLayers = defaultMutableLayers<String, Number>("A NAME")

    // TODO: Less cumbersome syntax for custom layers
    private val customLayers =
        DefaultMutableLayers<String, Number, DefaultMutableLayer<String, Number, *>>(
            "Test Custom Layers"
        ) { DefaultMutableLayer(it) }

    private val extendedLayers = TestLayers()

    @Test
    fun `should have a debuggable representation`() {
        "$defaultLayers" shouldBe """
A NAME: {}
0: <INIT>: {}(DefaultMutableLayer)
        """.trimIndent()
    }

    @Test
    fun `should be named`() {
        defaultLayers.name shouldBe "A NAME"
    }

    @Test
    fun `should be a computed map`() {
        defaultLayers.edit {
            this["A KEY"] = latestRule(7)
        }
        defaultLayers.commitAndNext("First").edit {
            this["A KEY"] = 3.toValue()
        }

        defaultLayers shouldBe mapOf("A KEY" to 3)
    }

    @Test
    fun `should peek at top layer`() {
        // That this compiles *is* the test
        extendedLayers.current.foo()
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

            val value = getAs<Int>("OTHER KEY")

            value shouldBe 3
        }
    }

    @Test
    fun `should exclude specific rule`() {
        defaultLayers.edit {
            this["A KEY"] = constantRule(3)
        }
        defaultLayers.commitAndNext("TEST LAYER")

        val except: Rule<String, Number, Int> = ConstantRule(7)
        defaultLayers.edit {
            this["A KEY"] = except
        }

        defaultLayers.edit {
            getAs("A KEY", except) shouldBe 3
        }
    }

    @Test
    fun `should exclude current key from rules view`() {
        defaultLayers.edit {
            this["A KEY"] = rule<Int>("A RULE") { _, _, view ->
                view.keys.shouldBeEmpty()
                3
            }
        }

        defaultLayers["A KEY"] shouldBe 3
    }

    @Test
    fun `should run what-if-with scenarios`() {
        val whatIf = defaultLayers.whatIfWith {
            this["A KEY"] = constantRule(3)
        }

        whatIf shouldBe mapOf("A KEY" to 3)
        defaultLayers shouldBe emptyMap()
    }

    @Test
    fun `should run what-if-without scenarios`() {
        defaultLayers.edit {
            this["A KEY"] = constantRule(3)
        }

        val whatIf = defaultLayers.whatIfWithout(defaultLayers.current)

        whatIf shouldBe emptyMap()
        defaultLayers shouldBe mapOf("A KEY" to 3)
    }

    @Test
    fun `should add a sub-type layer and use it`() {
        val layer = customLayers.commitAndNext { TestCustomMutableLayer() }

        // That this compiles *is* the test
        layer.foo()
    }

    @Test
    fun `should have a customized layers`() {
        val layer =
            extendedLayers.commitAndNext { TestCustomMutableSubLayer() }

        // That this compiles *is* the test
        layer.bar()
    }
}

private open class TestCustomMutableLayer(
    name: String = "TEST",
) : DefaultMutableLayer<String, Number, TestCustomMutableLayer>(name) {
    fun foo() = Unit
}

private open class TestCustomMutableSubLayer :
    TestCustomMutableLayer("SUB-TEST") {
    fun bar() = Unit
}

private class TestLayers :
    DefaultMutableLayers<String, Number, TestCustomMutableLayer>(
        "TESTS",
        defaultMutableLayer = { TestCustomMutableLayer(it) },
    )
