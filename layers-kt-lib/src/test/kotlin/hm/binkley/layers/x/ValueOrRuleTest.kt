package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValueOrRuleTest {
    @Test
    fun `should have debuggable presentation for values`() {
        Value(3).toString() shouldBe "<Value>: 3"
    }

    @Test
    fun `should retain value`() {
        3.toValue() shouldBe Value(3)
        3.toValue().value shouldBe 3
    }

    @Test
    fun `should have debuggable presentation for rules`() {
        object : Rule<String, Any, Int>() {
            override fun description() = "Test"
            override fun invoke(
                key: String,
                values: List<Int>,
                layers: Layers<String, Any>,
            ): Int = 0
        }.toString() shouldBe "<Rule>: Test"
    }

    @Test
    fun `should have named rules`() {
        namedRule<String, Any, Int>("Test") { _, _, _ ->
            0
        }.toString() shouldBe "<Rule>: Test"
    }
}
