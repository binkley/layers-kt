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
        maps.keys.isEmpty().shouldBeTrue()
        maps.entries.isEmpty().shouldBeTrue()
        maps.values.isEmpty().shouldBeTrue()
    }
}
