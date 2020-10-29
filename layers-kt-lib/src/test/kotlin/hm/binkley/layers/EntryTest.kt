package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class EntryTest {
    @Test
    fun `should retain value`() {
        Value(3).value shouldBe 3
    }

    @Test
    fun `should have a debuggable presentation`() {
        "${Value(3)}" shouldBe "Value: 3"
        "$TestRule" shouldBe "Rule: Test Fooby"
    }
}

private object TestRule : Rule<String>() {
    override fun invoke(values: List<String>) = "Fooby"
    override fun description() = "Test Fooby"
}
