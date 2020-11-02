package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class EntryTest {
    @Test
    fun `should retain value`() {
        Value(3).value shouldBe 3
    }

    @Test
    fun `should have a debuggable presentation`() {
        "${Value(3)}" shouldBe "<Value>: 3"
        "$TestRule" shouldBe "<Rule>[$bobKey]: Test Fooby"
    }

    @Test
    fun `should have value based on key`() {
        KeyBasedRule(aliceKey)(listOf(), emptyMap()) shouldBe "good"
        KeyBasedRule(bobKey)(listOf(), emptyMap()) shouldBe "bad"
    }
}

private object TestRule : Rule<String>(bobKey) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>) =
        "Fooby"

    override fun description() = "Test Fooby"
}

private class KeyBasedRule(key: String) : Rule<String>(key) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>):
        String = when (key) {
            aliceKey -> "good"
            else -> "bad"
        }

    override fun description() = "Test key-based"
}

private const val aliceKey = "alice"
private const val bobKey = "bob"
