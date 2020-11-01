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
        "$TestRule" shouldBe "<Rule>[bob]: Test Fooby"
    }

    @Test
    fun `should have value based on key`() {
        KeyBasedRule("alice")(listOf()) shouldBe "good"
        KeyBasedRule("bob")(listOf()) shouldBe "bad"
    }
}

private object TestRule : Rule<String>("bob") {
    override fun invoke(values: List<String>) = "Fooby"
    override fun description() = "Test Fooby"
}

private class KeyBasedRule(key: String) : Rule<String>(key) {
    override fun invoke(values: List<String>): String = when (key) {
        "alice" -> "good"
        else -> "bad"
    }

    override fun description() = "Test key-based"
}
