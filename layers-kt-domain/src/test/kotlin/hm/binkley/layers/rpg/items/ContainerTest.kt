package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.RpgLayersEditMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ContainerTest {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should have a debuggable representation`() =
        "${character.commitAndNext { TestContainer(it) }}" shouldBe
                "[-]TEST CONTAINER: {ITEM-WEIGHT=<Value>11.1, TEST CONTAINER-WEIGHT=<Rule>Sum[Float]} -> null: []"

    @Test
    fun `should add an item`() {
        val testItem = TestItem()
        val item = character.commitAndNext { testItem }
        val unpacked = character.commitAndNext {
            TestContainer(it)
        }

        val packed = character.commitAndNext { unpacked.stow(item) }

        packed.contents shouldBe listOf(item)
        character["TEST CONTAINER-WEIGHT"] shouldBe testItem.weight
    }

    @Test
    fun `should remove an item`() {
        val item = character.commitAndNext { TestItem() }
        val packed = character.commitAndNext {
            TestContainer(it, contents = listOf(item))
        }

        val unpacked = character.commitAndNext { packed.unstow(item) }

        unpacked.contents shouldBe emptyList<Item<*>>()
        character["TEST CONTAINER-WEIGHT"] shouldBe 0.0f
    }
}

private class TestContainer(
    layers: RpgLayersEditMap,
    worn: Boolean = false,
    previous: TestContainer? = null,
    contents: List<TestItem> = listOf(),
) : Container<TestItem, TestContainer>(
    "TEST CONTAINER",
    11.1f,
    worn,
    previous,
    layers,
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
    ) = TestContainer(layers, worn, previous, contents)
}
