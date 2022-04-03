package hm.binkley.layers

import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import hm.binkley.layers.rules.constantRule
import hm.binkley.layers.rules.lastOrDefaultRule
import hm.binkley.util.emptyStack
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

internal class DefaultMutableLayersTest {
    private val defaultLayers = defaultMutableLayers<String, Number>("A NAME")

    // TODO: Less cumbersome syntax for custom layers
    @Suppress("UPPER_BOUND_VIOLATED_WARNING")
    private val customLayers =
        DefaultMutableLayers<String, Number, DefaultMutableLayer<String, Number, TestCustomMutableLayer>>(
            "Test Custom Layers",
            { TestCustomMutableLayer(it) }
        )

    private val extendedLayers = TestLayers()

    @Test
    fun `should have a debuggable representation`() {
        "$defaultLayers" shouldBe """
A NAME: {}
0 (DefaultMutableLayer): <INIT>: {}
        """.trimIndent()
    }

    @Test
    fun `should be named`() {
        defaultLayers.name shouldBe "A NAME"
    }

    @Test
    fun `should be a computed map`() {
        defaultLayers.edit {
            this["A KEY"] = lastOrDefaultRule(7)
        }
        defaultLayers.saveAndNext("First").edit {
            this["A KEY"] = 3
        }

        defaultLayers shouldBe mapOf("A KEY" to 3)
    }

    @Test
    fun `should peek at top layer`() {
        // That this compiles *is* the test
        extendedLayers.current.foo()
    }

    @Test
    fun `should get a key as typed`() {
        defaultLayers.edit {
            this["A KEY"] = constantRule(3)
        }

        defaultLayers.getAs<Int>("A KEY") shouldBe 3
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

        val whatIf = defaultLayers.whatIfWithout() // current

        whatIf shouldBe emptyMap()
        defaultLayers shouldBe mapOf("A KEY" to 3)
    }

    @Test
    fun `should add a sub-type layer and use it`() {
        val layer = customLayers.saveAndNext { TestCustomMutableLayer() }

        // That this compiles *is* the test
        layer.foo()
    }

    @Test
    fun `should have a customized layer`() {
        val layer = customLayers.saveAndNext {
            TestCustomMutableSubLayer()
        }

        // That this compiles *is* the test
        layer.bar()
    }

    @Test
    fun `should have custom history`() {
        customLayers.history.first().shouldBeTypeOf<TestCustomMutableLayer>()
    }

    @Test
    fun `should undo`() {
        defaultLayers.undo()

        defaultLayers.history shouldBe emptyStack()
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
