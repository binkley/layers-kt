package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class EditMapTest {
    @Test
    fun `should have a debuggable representation for constant rules`() {
        val rule = TestEditMap().constantRule(7)

        "$rule" shouldBe "<Rule>: Constant(value=7)"
    }

    @Test
    fun `should run constant rules`() {
        val rule = TestEditMap().constantRule(7)
        val value = rule("A RULE", listOf(), mapOf())

        value shouldBe 7
    }

    @Test
    fun `should have a debuggable representation for latest-of rules`() {
        val rule = TestEditMap().latestOfRule(7)

        "$rule" shouldBe "<Rule>: Latest(default=7)"
    }

    @Test
    fun `should default for latest-of rules`() {
        val rule = TestEditMap().latestOfRule(7)
        val value = rule("A RULE", emptyList(), emptyMap())

        value shouldBe 7
    }

    @Test
    fun `should run latest-of rules`() {
        val rule = TestEditMap().latestOfRule(7)
        val value = rule("A RULE", listOf(1, 2, 3), emptyMap())

        value shouldBe 3
    }
}

private class TestEditMap(
    private val map: MutableMap<String, ValueOrRule<Number>> = mutableMapOf(),
) : EditMap<String, Number>, MutableMap<String, ValueOrRule<Number>> by map {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Number> getOtherValueAs(key: String): T =
        throw IllegalStateException("BUG: Not supported by test edit map")
}
