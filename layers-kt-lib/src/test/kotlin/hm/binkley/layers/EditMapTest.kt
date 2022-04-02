package hm.binkley.layers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.maps.shouldNotHaveKey
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class EditMapTest {
    @Test
    fun `should put unwrapped values`() {
        val editMap = TestEditMap()

        editMap["BOB"] = 17

        editMap["BOB"] shouldBe 17.toValue()
    }

    @Test
    fun `should edit by delegate`() {
        val editMap = TestEditMap()

        editMap.BOB = 16
        ++editMap.BOB

        editMap["BOB"] shouldBe 17.toValue()
    }

    @Test
    fun `should remove by delegate`() {
        val editMap = TestEditMap(
            mutableMapOf(
                "NANCY" to 3.toValue()
            )
        )

        editMap.NANCY = null

        editMap shouldNotHaveKey "NANCY"
    }

    @Test
    fun `should complain when editing by delegate for a rule`() {
        val editMap = TestEditMap()

        editMap["BOB"] = editMap.rule<Int>("BOB") { _, _, _ -> 17 }

        shouldThrow<ClassCastException> {
            ++editMap.BOB
        }
    }

    @Test
    fun `should complain when editing by delegate for missing key`() {
        val editMap = TestEditMap()

        shouldThrow<IllegalStateException> {
            editMap.BOB
        }
    }
}

private var TestEditMap.BOB: Int by EditMapDelegate { name }
private var TestEditMap.NANCY: Int? by EditMapDelegate { name }
