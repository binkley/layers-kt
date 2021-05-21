package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XRuleTest {
    @Test
    fun `should have debuggable representation`() =
        "$testRule" shouldBe "<Rule>: Test Rule"
}

private interface Foo
private class Bar : Foo {
    override fun toString() = "I AM BAR!"
}

private val testRule = object : XRule<Foo, Bar>("Test Rule") {
    override fun invoke(values: List<Bar>, layers: XLayers<*, Foo, *>) =
        object : Foo {}
}