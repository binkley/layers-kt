package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class XValueTest {
    @Test
    fun `should have debuggable representation`() =
        "${XValue(3)}" shouldBe "<Value[Int]>: 3"
}
