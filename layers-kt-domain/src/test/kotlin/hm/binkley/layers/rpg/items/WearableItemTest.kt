package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.rules.WornRule
import hm.binkley.layers.rules.constantRule
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class WearableItemTest {
    private val character = character("TEST CHARACTER")

    init {
        character.edit {
            this["A KEY"] = constantRule(3)
        }
    }

    @Test
    fun `should have a debuggable representation`() {
        val newItemA = character.saveAndNext { TestWearableItem() }
        "$newItemA" shouldBe
            "[-]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> null"
        "${newItemA.don()}" shouldBe
            "[+]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> TEST ITEM"

        val newItemB = character.saveAndNext { TestWearableItem(true) }
        "$newItemB" shouldBe
            "[+]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> null"
        "${newItemB.doff()}" shouldBe
            "[-]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> TEST ITEM"
    }

    @Test
    fun `should ignore unworn items`() {
        val item = character.saveAndNext { TestWearableItem() }

        character["A KEY"] shouldBe 3
        item.worn.shouldBeFalse()
    }

    @Test
    fun `should use worn items`() {
        val item = character.saveAndNext { TestWearableItem().don() }

        character["A KEY"] shouldBe 7
        item.worn.shouldBeTrue()
    }

    @Test
    fun `should toggle wornness`() {
        val newItem = character.saveAndNext { TestWearableItem() }
        character["A KEY"] shouldBe 3

        val donnedItem = character.saveAndNext { newItem.don() }
        character["A KEY"] shouldBe 7

        character.saveAndNext { donnedItem.doff() }
        character["A KEY"] shouldBe 3
    }

    @Test
    fun `should complain to don an already donned item`() {
        shouldThrow<IllegalStateException> {
            character.saveAndNext { TestWearableItem() }.don().don()
        }
    }

    @Test
    fun `should complain to doff an already doffed item`() {
        shouldThrow<IllegalStateException> {
            // New item begin doffed
            character.saveAndNext { TestWearableItem() }.doff()
        }
    }
}

private class TestWearableItem(
    worn: Boolean = false,
    previous: TestWearableItem? = null,
) : WearableItem<TestWearableItem>(
    "TEST ITEM",
    13.13f,
    worn,
    previous,
) {
    init {
        edit {
            val rule = constantRule(7)
            this["A KEY"] = WornRule(rule.name, rule, self)
        }
    }

    override fun change(previous: TestWearableItem, worn: Boolean) =
        TestWearableItem(worn, previous)
}
