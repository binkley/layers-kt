package hm.binkley.layers.x

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableMapListTest {
    @Test
    fun `should have a debuggable presentation`() {
        val maps = MutableMapList<String, Any>()
        maps.toString() shouldBe "0: {}"

        maps.add(0, mutableMapOf("BOB" to Value(1)))
        maps.toString() shouldBe "0: {}\n1: {BOB=<Value>: 1}"
    }

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
    fun `should start with provided history`() {
        val maps =
            MutableMapList(mutableListOf(mutableMapOf("BOB" to testRule)))

        maps.history.size shouldBe 1
    }

    @Test
    fun `should put key-value to current map`() {
        val maps = MutableMapList<String, Any>()
        maps["BOB"] = testRule

        maps.size shouldBe 1
    }

    @Test
    fun `should clear current`() {
        val maps = MutableMapList<String, Any>()
        maps["BOB"] = testRule

        maps.add(0, mutableMapOf())
        maps["NANCY"] = testRule

        maps.clear()

        maps.size shouldBe 0
        maps.view().size shouldBe 1
    }

    @Test
    fun `should handle missing keys`() {
        val maps = MutableMapList<String, Any>()

        maps.view()["BOB"] shouldBe null
    }

    @Test
    fun `should complain when missing a rule`() {
        val maps = MutableMapList<String, Any>()
        maps["BOB"] = Value(3)

        shouldThrow<IllegalStateException> {
            maps.view()["BOB"]
        }
    }

    @Test
    fun `should follow a rule`() {
        val testKey = "BOB"

        val maps = MutableMapList<String, Any>()
        maps[testKey] = testRule

        maps.add(0, mutableMapOf())
        maps[testKey] = Value(1)

        maps.add(0, mutableMapOf())
        maps[testKey] = Value(2)

        maps.view()[testKey] shouldBe 3
    }
}

val testRule = object : Rule<String, Any>() {
    override fun invoke(
        values: List<Any>,
        history: MutableMapList<String, Any>,
    ): Int = values.sumOf { it as Int }

    override fun description() = "Sum[Int]"
}
