package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.rules.NotWornRule
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
        val newItemA = character.commitAndNext { TestWearableItem() }
        "$newItemA" shouldBe
            "[-]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> null"
        "${newItemA.don()}" shouldBe
            "[+]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> TEST ITEM"

        val newItemB = character.commitAndNext { TestWearableItem(true) }
        "$newItemB" shouldBe
            "[+]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> null"
        "${newItemB.doff()}" shouldBe
            "[-]TEST ITEM: {ITEM-WEIGHT=<Value>13.13, A KEY=<Rule>Constant(value=7)} -> TEST ITEM"
    }

    @Test
    fun `should ignore inactive items`() {
        val item = character.commitAndNext { TestWearableItem() }

        character["A KEY"] shouldBe 3
        item.worn.shouldBeFalse()
    }

    @Test
    fun `should use active items`() {
        val item = character.commitAndNext { TestWearableItem().don() }

        character["A KEY"] shouldBe 7
        item.worn.shouldBeTrue()
    }

    @Test
    fun `should use toggle activeness`() {
        val newItem = character.commitAndNext { TestWearableItem() }
        character["A KEY"] shouldBe 3

        val donnedItem = character.commitAndNext { newItem.don() }
        character["A KEY"] shouldBe 7

        character.commitAndNext { donnedItem.doff() }
        character["A KEY"] shouldBe 3
    }

    @Test
    fun `should complain to don an already donned item`() {
        shouldThrow<IllegalStateException> {
            character.commitAndNext { TestWearableItem() }.don().don()
        }
    }

    @Test
    fun `should complain to doff an already doffed item`() {
        shouldThrow<IllegalStateException> {
            // New item starteds doffed
            character.commitAndNext { TestWearableItem() }.doff()
        }
    }
}

private class TestWearableItem(
    active: Boolean = false,
    previous: TestWearableItem? = null,
) : WearableItem<TestWearableItem>(
    "TEST ITEM",
    13.13f,
    active,
    previous,
) {
    init {
        edit {
            val rule = constantRule(7)
            this["A KEY"] =
                if (active) rule
                else NotWornRule(rule.name, this@TestWearableItem)
        }
    }

    override fun activateNext(worn: Boolean, previous: TestWearableItem) =
        TestWearableItem(worn, previous)
}
