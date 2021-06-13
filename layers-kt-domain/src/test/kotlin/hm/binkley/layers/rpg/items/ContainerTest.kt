package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ContainerTest {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should have a debuggable representation`() =
        "${character.saveAndNext { TestContainer() }}" shouldBe
            "[-]TEST CONTAINER: {ITEM-WEIGHT=<Value>11.1, TEST CONTAINER-WEIGHT=<Rule>Sum[Float]} -> null: []"

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
        character["TEST CONTAINER-WEIGHT"] shouldBe 0.0f
    }
}

private class TestContainer(
    worn: Boolean = false,
    previous: TestContainer? = null,
    contents: List<TestItem> = listOf(),
) : Container<TestItem, TestContainer>(
    "TEST CONTAINER",
    11.1f,
    worn,
    previous,
    contents
) {
    override fun activateNext(
        worn: Boolean,
        previous: TestContainer,
    ) = updateContainer(worn, previous, contents)

    override fun updateContainer(
        worn: Boolean,
        previous: TestContainer?,
        contents: List<TestItem>,
    ) = TestContainer(worn, previous, contents)
}
