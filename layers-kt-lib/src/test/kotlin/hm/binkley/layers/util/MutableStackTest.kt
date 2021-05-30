package hm.binkley.layers.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableStackTest {
    @Test
    fun `should be a list`() {
        mutableStackOf<Int>() shouldBe mutableListOf()
        mutableStackOf(3) shouldBe mutableListOf(3)
    }

    @Test
    fun `should copy`() {
        val prior = mutableListOf(3)
        val stack = XArrayMutableStack(prior)

        prior.removeFirst()

        stack shouldBe listOf(3)
    }

    @Test
    fun `should peek`() {
        val stack = mutableStackOf(3)

        stack.peek() shouldBe 3
    }

    @Test
    fun `should push`() {
        val stack = mutableStackOf<Int>()

        stack.push(3)

        stack shouldBe stackOf(3)
    }

    @Test
    fun `should pop`() {
        val stack = mutableStackOf(3)

        val element = stack.pop()

        element shouldBe 3
        stack shouldBe stackOf()
    }
}
