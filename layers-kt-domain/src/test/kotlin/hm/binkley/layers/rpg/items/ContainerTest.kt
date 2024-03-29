package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.items.Weight.Companion.ZERO
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ContainerTest {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should have a debuggable representation`() =
        "${character.saveAndNext { TestContainer() }}" shouldBe
            "[-]TEST CONTAINER: {ITEM-WEIGHT=<Value>11.10, TEST CONTAINER-WEIGHT=<Rule>Sum[Weight]} -> null: []"

    @Test
    fun `should add an item`() {
        val testItem = TestItem()
        val item = character.saveAndNext { testItem }
        val unpacked = character.saveAndNext {
            TestContainer()
        }

        val packed = character.saveAndNext { unpacked.stow(item) }

        packed.contents shouldBe listOf(item)
        character["TEST CONTAINER-WEIGHT"] shouldBe testItem.weight
    }

    @Test
    fun `should remove an item`() {
        val item = character.saveAndNext { TestItem() }
        val packed = character.saveAndNext {
            TestContainer(contents = listOf(item))
        }

        val unpacked = character.saveAndNext { packed.unstow(item) }

        unpacked.contents shouldBe emptyList<Item<*>>()
        character["TEST CONTAINER-WEIGHT"] shouldBe ZERO
    }
}

private class TestContainer(
    worn: Boolean = false,
    previous: TestContainer? = null,
    contents: List<TestItem> = listOf(),
) : Container<TestItem, TestContainer>(
    "TEST CONTAINER",
    11.1f.weight,
    worn,
    previous,
    contents
) {
    override fun change(
        previous: TestContainer,
        worn: Boolean,
    ) = updateContainer(worn, previous, contents)

    override fun updateContainer(
        worn: Boolean,
        previous: TestContainer?,
        contents: List<TestItem>,
    ) = TestContainer(worn, previous, contents)
}
