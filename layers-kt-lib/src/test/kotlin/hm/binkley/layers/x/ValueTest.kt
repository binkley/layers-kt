package hm.binkley.layers.x

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValueTest {
    @Test
    fun `should have a debuggable representation`() {
        "${Value(3)}" shouldBe "<Value>: 3"
    }

    @Test
    fun `should have a value`() {
        Value(3).value shouldBe 3
    }

    @Test
    fun `should have pleasant way to create`() {
        3.toValue() shouldBe Value(3)
    }
}
