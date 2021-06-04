package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.newCharacter
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.rules.PassThruRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ActiveItemTest {
    private val character = newCharacter("TEST CHARACTER")

    init {
        character.edit {
            this["A KEY"] = constantRule(3)
        }
    }

    @Test
    fun `should ignore inactive items`() {
        character.commitAndNext { TestItem(it).doff() }

        character["A KEY"] shouldBe 3
    }

    @Test
    fun `should use active items`() {
        character.commitAndNext { TestItem(it).don() }

        character["A KEY"] shouldBe 7
    }
}

private class TestItem(
    private val layers: RpgLayersEditMap,
    active: Boolean = false,
) : ActiveItem<TestItem>("TEST ITEM", active, layers) {
    init {
        edit {
            this["A KEY"] =
                if (active) constantRule(7)
                else PassThruRule(this@TestItem, layers)
        }
    }

    override fun new(active: Boolean) = TestItem(layers, active)
}
