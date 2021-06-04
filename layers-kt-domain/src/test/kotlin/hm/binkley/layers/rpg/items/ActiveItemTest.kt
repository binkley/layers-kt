package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.newCharacter
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.rules.PassThruRule
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
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
    fun `should have a debuggable representation`() {
        val item = character.commitAndNext { TestItem(it) }

        "${item.don()}" shouldBe "[+]TEST ITEM: {A KEY=<Rule>Constant(value=7)}"
        "${item.doff()}" shouldBe "[-]TEST ITEM: {A KEY=<Rule>Constant(value=7)}"
    }

    @Test
    fun `should ignore inactive items`() {
        val item = character.commitAndNext { TestItem(it).doff() }

        character["A KEY"] shouldBe 3
        item.active.shouldBeFalse()
    }

    @Test
    fun `should use active items`() {
        val item = character.commitAndNext { TestItem(it).don() }

        character["A KEY"] shouldBe 7
        item.active.shouldBeTrue()
    }
}

private class TestItem(
    private val layers: RpgLayersEditMap,
    active: Boolean = false,
) : ActiveItem<TestItem>("TEST ITEM", active, layers) {
    init {
        edit {
            val rule = constantRule(7)
            this["A KEY"] =
                if (active) rule
                else PassThruRule(rule.name, this@TestItem, layers)
        }
    }

    override fun new(active: Boolean) = TestItem(layers, active)
}
