package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValueOrRuleTest {
    @Test
    fun `should retain value`() {
        Value(3).value shouldBe 3
    }

    @Test
    fun `should have a debuggable presentation`() {
        "${Value(3)}" shouldBe "<Value>: 3"
        "$TestRule" shouldBe "<Rule>: Test Fooby"
        "${ruleFor<Int> { _, _, _ -> 3 }}" shouldBe
                "<Rule>: <Anonymous>"
    }

    @Test
    fun `should support rules dependent on another layer value`() {
        DependentRule()(
            fredRule,
            listOf(),
            mapOf(bobKey to 13)
        ) shouldBe 6
    }
}

private object TestRule : NamedRule<String>("Test Fooby") {
    override fun invoke(
        key: String,
        values: List<String>,
        allValues: ValueMap,
    ) = "Fooby"
}

private class DependentRule :
    NamedRule<Int>("Depends on $bobKey's value") {
    override fun invoke(
        key: String,
        values: List<Int>,
        allValues: ValueMap,
    ) = (allValues[bobKey] as Int) / 2
}

private const val aliceKey = "alice"
private const val bobKey = "BOB"
private const val fredRule = "fred"
