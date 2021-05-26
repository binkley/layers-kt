package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XValueTest {
    @Test
    fun `should have a debuggable view`() =
        "${3.toValue()}" shouldBe "<Value[Int]>: 3"
}
