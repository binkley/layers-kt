package hm.binkley.layers.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValueOrRuleTest {
    @Test
    fun `should have a debuggable representation`() {
        Value(3).toString() shouldBe "<Value>: 3"
        object : Rule<String, Int>() {
            override fun invoke(
                key: String,
                values: List<Int>,
                history: MutableMapList<String, Int>,
            ): Int = values.sum()

            override fun description() = "Sum[Int]"
        }.toString() shouldBe "<Rule>: Sum[Int]"
    }

    @Test
    fun `should retain value`() {
        Value(3).value shouldBe 3
    }
}
