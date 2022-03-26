package hm.binkley.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableStackTest {
    @Test
    fun `should be a list`() {
        mutableStackOf<Int>() shouldBe mutableListOf()
        mutableStackOf(3) shouldBe mutableListOf(3)
        mutableStackOf(3).toStack() shouldBe listOf(3)
    }

    @Test
    fun `should defensively copy`() {
        val prior = mutableListOf(3)
        val stack = prior.toMutableStack()

        prior.removeFirst()

        stack shouldBe listOf(3)
    }

    @Test
    fun `should pre-size`() {
        val stack = ArrayMutableStack<Int>(1)

        stack.push(1)
        stack.push(2)

        stack shouldBe listOf(1, 2)
    }

    @Test
    fun `should peek`() {
        val stack = mutableStackOf(3)

        stack.peek() shouldBe 3
    }

    @Test
    fun `should push`() {
        val stack = mutableStackOf<Int>()

        val element = stack.push(3)

        element shouldBe 3
        stack shouldBe listOf(3)
    }

    @Test
    fun `should pop`() {
        val stack = mutableStackOf(3)

        val element = stack.pop()

        element shouldBe 3
        stack shouldBe emptyList()
    }

    @Test
    fun `should complain on pop when empty`() {
        val stack = emptyMutableStack<Int>()

        shouldThrow<NoSuchElementException> {
            stack.pop()
        }
    }

    @Test
    fun `should duplicate`() {
        val stack = mutableStackOf(3)

        stack.duplicate()

        stack shouldBe listOf(3, 3)
    }

    @Test
    fun `should rotate top N`() {
        val stack = mutableStackOf(1, 2, 3, 4)

        stack.rotate(4)

        stack shouldBe listOf(4, 1, 2, 3)
    }

    @Test
    fun `should rotate top 3 by default`() {
        val stack = mutableStackOf(1, 2, 3, 4)

        stack.rotate()

        stack shouldBe listOf(1, 4, 2, 3)
    }

    @Test
    fun `should rotate top 0`() {
        val stack = mutableStackOf(1, 2, 3, 4)

        stack.rotate(0)

        stack shouldBe listOf(1, 2, 3, 4)
    }

    @Test
    fun `should counter-rotate top N`() {
        val stack = mutableStackOf(1, 2, 3, 4)

        stack.rotate(-3)

        stack shouldBe listOf(1, 3, 4, 2)
    }

    @Test
    fun `should swap top 2`() {
        val stack = mutableStackOf(1, 2, 3, 4)

        stack.swap()

        stack shouldBe listOf(1, 2, 4, 3)
    }
}
