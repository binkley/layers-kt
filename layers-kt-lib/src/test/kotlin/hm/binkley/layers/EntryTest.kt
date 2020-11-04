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

    @Test
    fun `should support rules dependent on another layer value`() {
        DependentRule(fredRule)(listOf(), mapOf(bobKey to 13)) shouldBe 6
    }
}

private object TestRule : Rule<String>(bobKey) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>) =
        "Fooby"

    override fun description() = "Test Fooby"
}

private class KeyBasedRule(key: String) : Rule<String>(key) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>) =
        when (key) {
            aliceKey -> "good"
            else -> "bad"
        }

    override fun description() = "Test key-based"
}

private class DependentRule(key: String) : Rule<Int>(key) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>): Int {
        return (allValues[bobKey] as Int) / 2
    }

    override fun description() = "Depends on Bob's value"
}

private const val aliceKey = "alice"
private const val bobKey = "bob"
private const val fredRule = "fred"
