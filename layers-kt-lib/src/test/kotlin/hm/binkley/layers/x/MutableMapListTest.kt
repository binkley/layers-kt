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
    fun `should put key-value to current map`() {
        val maps = MutableMapList<String, Any>()
        maps["BOB"] = testRule

        maps.size shouldBe 1
    }

    @Test
    fun `should clear current`() {
        val maps = MutableMapList<String, Any>()
        maps["BOB"] = testRule

        maps.add(mutableMapOf())
        maps["NANCY"] = testRule

        maps.clear()

        maps.size shouldBe 0
        maps.view().size shouldBe 1
    }

    @Test
    fun `should follow a rule`() {
        val testKey = "BOB"

        val maps = MutableMapList<String, Any>()
        maps[testKey] = testRule

        maps.add(mutableMapOf())
        maps[testKey] = Value(1)

        maps.add(mutableMapOf())
        maps[testKey] = Value(2)

        maps.view()[testKey] shouldBe 3
    }
}

val testRule = object : Rule<String, Any>() {
    override fun invoke(
        key: String,
        values: List<Any>,
        history: MutableMapList<String, Any>,
    ): Int = values.sumOf { it as Int }
}
