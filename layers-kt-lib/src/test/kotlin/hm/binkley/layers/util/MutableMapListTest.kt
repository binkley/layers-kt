package hm.binkley.layers.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

private typealias TestLayers = MutableMapList<String, Any>

internal class MutableMapListTest {
    @Test
    fun `should have a debuggable presentation`() {
        val maps = TestLayers()
        maps.toString() shouldBe "0: {}"

        maps.add(mutableMapOf("BOB" to Value(1)))
        maps.toString() shouldBe "0: {}\n1: {BOB=<Value>: 1}"
    }

    @Test
    fun `should start empty but usable`() {
        val maps = TestLayers()

        maps.size shouldBe 0
        maps.isEmpty().shouldBeTrue()
        maps.entries.size shouldBe 0
        maps.keys.size shouldBe 0
        maps.values.size shouldBe 0

        maps.history.size shouldBe 1
    }

    @Test
    fun `should start with provided history`() {
        val maps = TestLayers(mutableListOf(mutableMapOf("BOB" to testRule)))

        maps.history.size shouldBe 1
    }

    @Test
    fun `should put key-value to current map`() {
        val maps = TestLayers()
        maps["BOB"] = testRule

        maps.size shouldBe 1
    }

    @Test
    fun `should clear current`() {
        val maps = TestLayers()
        maps["BOB"] = testRule

        maps.add(mutableMapOf())
        maps["NANCY"] = testRule

        maps.clear()

        maps.size shouldBe 0
        maps.toComputedMap().size shouldBe 1
    }

    @Test
    fun `should handle missing keys`() {
        val maps = TestLayers()

        maps.toComputedMap()["BOB"] shouldBe null
    }

    @Test
    fun `should complain when missing a rule`() {
        val maps = TestLayers()
        maps["BOB"] = Value(3)

        shouldThrow<IllegalStateException> {
            maps.toComputedMap()["BOB"]
        }
    }

    @Test
    fun `should follow a rule`() {
        val testKey = "BOB"

        val maps = TestLayers()
        maps[testKey] = testRule

        maps.add(mutableMapOf())
        maps[testKey] = Value(1)

        maps.add(mutableMapOf())
        maps[testKey] = Value(2)

        maps.toComputedMap()[testKey] shouldBe 3
    }
}

val testRule = object : Rule<String, Any>() {
    override fun invoke(
        key: String,
        values: List<Any>,
        history: TestLayers,
    ): Int = values.sumOf { it as Int }

    override fun description() = "Sum[Int]"
}
