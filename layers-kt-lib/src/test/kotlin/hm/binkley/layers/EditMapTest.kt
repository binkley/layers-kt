package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class EditMapTest {
    @Test
    fun `should make new rules`() {
        val rule = TestEditMap().rule<Int>("TEST") { key, values, view ->
            key shouldBe "FOO"
            values shouldBe listOf(1, 2, 3)
            view shouldBe mapOf("BAR" to 3.14159)
            key.length + values.size + view.size
        }

        val value = rule("FOO", listOf(1, 2, 3), mapOf("BAR" to 3.14159))

        value shouldBe 7
    }
}
