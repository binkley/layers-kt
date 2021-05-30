package hm.binkley.layers.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `should be a list`() {
        stackOf<Int>() shouldBe listOf()
        stackOf(3) shouldBe listOf(3)
    }

    @Test
    fun `should copy`() {
        val prior = mutableListOf(3)
        val stack = XArrayStack(prior)

        prior.removeFirst()

        stack shouldBe listOf(3)
    }

    @Test
    fun `should peek`() {
        val stack = stackOf(3)

        stack.peek() shouldBe 3
    }
}
