package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

private const val testRuleKey = "BOB"
private const val testRuleName = "Test Rule"

internal class XRuleTest {
    @Test
    fun `should have a debuggable view`() =
        "$testRule" shouldBe "<Rule/BOB>: Test Rule"

    @Test
    fun `should be keyed`() = testRule.key shouldBe testRuleKey

    @Test
    fun `should be named`() = testRule.name shouldBe testRuleName
}

private interface Foo
private object Bar : Foo

private val testRule = object : XRule<String, Foo, Bar>(
    testRuleKey,
    testRuleName
) {
    override fun invoke(
        key: String,
        values: List<Bar>,
        layers: XLayersForRules<String, Foo, *>,
    ): Bar = Bar
}
