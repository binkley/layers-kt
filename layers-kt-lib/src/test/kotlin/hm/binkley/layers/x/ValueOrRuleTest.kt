package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValueOrRuleTest {
    @Test
    fun `should have a debuggable representation`() {
        Value(3).toString() shouldBe "<Value>: 3"
        object : Rule<String, Int>() {
            override fun invoke(
                values: List<Int>,
                history: MutableMapList<String, Int>,
            ): Int = values.sum()

            override fun description() = "Sum[Int]"
        }.toString() shouldBe "<Rule>: Sum[Int]"
    }
}
