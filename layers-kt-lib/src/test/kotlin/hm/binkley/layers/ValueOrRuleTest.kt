package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class ValueOrRuleTest {
    @Test
    fun `should retain value`() {
        Value(3).value shouldBe 3
    }

    @Test
    fun `should have a debuggable presentation`() {
        "${Value(3)}" shouldBe "<Value>: 3"
        "$TestRule" shouldBe "<Rule>[$bobKey]: Test Fooby"
        "${ruleFor<Int>(bobKey) { _, _, _ -> 3 }}" shouldBe
            "<Rule>[bob]: <Anonymous>"
    }

    @Test
    fun `should have value based on key`() {
        KeyBasedRule(aliceKey)(aliceKey, listOf(), emptyMap()) shouldBe "good"
        KeyBasedRule(bobKey)(bobKey, listOf(), emptyMap()) shouldBe "bad"
    }

    @Test
    fun `should support rules dependent on another layer value`() {
        DependentRule(fredRule)(
            fredRule,
            listOf(),
            mapOf(bobKey to 13)
        ) shouldBe 6
    }
}

private object TestRule : NamedRule<String>("Test Fooby", bobKey) {
    override fun invoke(
        key: String,
        values: List<String>,
        allValues: ValueMap,
    ) = "Fooby"
}

private class KeyBasedRule(key: String) :
    NamedRule<String>("Test key-based", key) {
    override fun invoke(
        key: String,
        values: List<String>,
        allValues: ValueMap,
    ) = when (key) {
        aliceKey -> "good"
        else -> "bad"
    }
}

private class DependentRule(key: String) :
    NamedRule<Int>("Depends on Bob's value", key) {
    override fun invoke(
        key: String,
        values: List<Int>,
        allValues: ValueMap,
    ) = (allValues[bobKey] as Int) / 2
}

private const val aliceKey = "alice"
private const val bobKey = "bob"
private const val fredRule = "fred"
