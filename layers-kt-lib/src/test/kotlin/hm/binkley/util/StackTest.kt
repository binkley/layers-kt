package hm.binkley.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `should be a list`() {
        emptyStack<Int>() shouldBe listOf()
        stackOf(3) shouldBe listOf(3)
    }

    @Test
    fun `should defensively copy`() {
        val prior = mutableListOf(3)
        val stack = prior.toStack()

        prior.removeFirst()

        stack shouldBe listOf(3)
    }

    @Test
    fun `should peek`() {
        val stack = stackOf(3)

        stack.peek() shouldBe 3
    }

    @Test
    fun `should complain on peek when empty`() {
        val stack = emptyStack<Int>()

        shouldThrow<NoSuchElementException> {
            stack.peek()
        }
    }
}
