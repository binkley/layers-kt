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
            "[-]TEST CONTAINER: {} -> null: []"

    @Test
    fun `should add an item`() {
        val item = character.commitAndNext { TestItem() }
        val container = character.commitAndNext {
            TestContainer(it)
        }.stow(item)

        container.contents shouldBe listOf(item)
    }

    @Test
    fun `should remove an item`() {
        val item = character.commitAndNext { TestItem() }
        val container = character.commitAndNext {
            TestContainer(it, contents = listOf(item))
        }.unstow(item)

        container.contents shouldBe emptyList<Item<*>>()
    }
}

private class TestContainer(
    layers: RpgLayersEditMap,
    worn: Boolean = false,
    previous: TestContainer? = null,
    contents: List<TestItem> = listOf(),
) : Container<TestItem, TestContainer>(
    "TEST CONTAINER",
    layers,
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
    ) = TestContainer(layers, worn, previous, contents)
}
