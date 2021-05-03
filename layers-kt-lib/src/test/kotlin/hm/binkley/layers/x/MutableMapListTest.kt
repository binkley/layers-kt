package hm.binkley.layers.x

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableMapListTest {
    @Test
    fun `should start empty but usable`() {
        val maps = MutableMapList<String, Any>()

        maps.size shouldBe 0
        maps.isEmpty().shouldBeTrue()
        maps.entries.size shouldBe 0
        maps.keys.size shouldBe 0
        maps.values.size shouldBe 0

        maps.history.size shouldBe 1
    }

    @Test
    fun `should follow a rule`() {
        val testKey = "BOB"

        val maps = MutableMapList<String, Any>()
        maps.edit {
            this[testKey] = object : Rule<String, Any>() {
                override fun invoke(
                    key: String,
                    values: List<Any>,
                    history: MutableMapList<String, Any>,
                ): Int = values.sumOf { it as Int }
            }
        }

        maps.add(mutableMapOf())
        maps.edit {
            this[testKey] = Value(1)
        }

        maps.add(mutableMapOf())
        maps.edit {
            this[testKey] = Value(2)
        }

        maps.view()[testKey] shouldBe 3
    }
}
