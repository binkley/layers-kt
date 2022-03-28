package hm.binkley.util

import hm.binkley.util.ArrayMutableStack.Companion.asMutableStack
import hm.binkley.util.ArrayMutableStack.Companion.asStack
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `should be a list`() {
        emptyStack<Int>() shouldBe listOf()
        stackOf(3) shouldBe listOf(3)
        mutableStackOf<Int>() shouldBe mutableListOf()
        mutableStackOf(3) shouldBe mutableListOf(3)
        mutableStackOf(3).toStack() shouldBe listOf(3)
    }

    @Test
    fun `should defensively copy`() {
        val prior = mutableListOf(3, 4, 5)
        val stack = prior.toStack()

        prior.removeFirst()

        stack shouldBe listOf(3, 4, 5)

        val mutableStack = prior.toMutableStack()

        prior.removeFirst()

        mutableStack shouldBe listOf(4, 5)
    }

    @Test
    fun `should wrap`() {
        val prior = mutableListOf(3, 4, 5)
        val stack = prior.asStack()

        prior.removeFirst()

        stack shouldBe listOf(4, 5)

        val mutableStack = prior.asMutableStack()

        prior.removeFirst()

        mutableStack shouldBe listOf(5)
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

    @Test
    fun `should pre-size`() {
        val stack = ArrayMutableStack<Int>(1)

        stack.push(1)
        stack.push(2)

        stack shouldBe listOf(1, 2)
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
    fun `should push`() {
        val stack = mutableStackOf<Int>()

        val element = stack.push(3)

        element shouldBe 3
        stack shouldBe listOf(3)
    }

    @Test
    fun `should replace`() {
        val stack = mutableStackOf(3)

        val replace = stack.replace(4)

        replace shouldBe 3
        stack shouldBe listOf(4)
    }

    @Test
    fun `should complain on replace when empty`() {
        val stack = emptyMutableStack<Int>()

        shouldThrow<NoSuchElementException> {
            stack.replace(4)
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
