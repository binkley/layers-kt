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
