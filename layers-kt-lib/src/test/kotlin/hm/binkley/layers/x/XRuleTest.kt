package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XRuleTest {
    @Test
    fun `should have a debuggable view`() =
        "$testRule" shouldBe "<Rule/BOB>: Test Rule"
}

private interface Foo
private class Bar : Foo {
    override fun toString() = "I AM BAR!"
}

private val testRule = object : XRule<String, Foo, Bar>("BOB", "Test Rule") {
    override fun invoke(
        key: String,
        values: List<Bar>,
        layers: XLayers<*, Foo, *>,
    ): Bar = Bar()
}
