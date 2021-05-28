package hm.binkley.layers.x

import hm.binkley.layers.x.DefaultMutableLayers.Companion.defaultMutableLayers
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DefaultMutableLayersTest {
    private val layers = defaultMutableLayers<String, Number>("A NAME")

    @Test
    fun `should have a debuggable representation`() {
        "$layers" shouldBe """
A NAME: {}
0: {}
        """.trimIndent()
    }

    @Test
    fun `should be named`() {
        layers.name shouldBe "A NAME"
    }

    @Test
    fun `should be a computed map`() {
        layers.edit {
            this["A KEY"] = latestOfRule(7)
        }
        layers.commitAndNext().edit {
            this["A KEY"] = 3.toValue()
        }

        layers shouldBe mapOf("A KEY" to 3)
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

        layers.edit {
            this["A KEY"] = rule
        }

        layers["A KEY"] shouldBe 3
    }

    @Test
    fun `should read other values in layers`() {
        layers.edit {
            this["OTHER KEY"] = constantRule(3)

            val value = getOtherValueAs<Int>("OTHER KEY")

            value shouldBe 3
        }
    }

    @Test
    fun `should run what-if scenarios`() {
        val map = layers.whatIf {
            this["A KEY"] = constantRule(3)
        }

        map shouldBe mapOf("A KEY" to 3)
        layers shouldBe emptyMap()
    }
}
