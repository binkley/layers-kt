package hm.binkley.layers.x.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `should be a list`() {
        stackOf<Int>() shouldBe listOf()
        stackOf(3) shouldBe listOf(3)
    }

    @Test
    fun `should peek`() {
        val stack = stackOf(3)

        stack.peek() shouldBe 3
    }
}
