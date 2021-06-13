package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal abstract class ContainerTestBase<C : Container<TestItem, C>>(
    private val container: C,
) {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should add and remove an item`() {
        val empty = character.commitAndNext { container }
        val item = character.commitAndNext { TestItem() }

        val packed = character.commitAndNext { empty.stow(item) }
        packed.contents shouldBe listOf(item)

        val unpacked = character.commitAndNext { empty.unstow(item) }
        unpacked.contents shouldBe emptyList()
    }
}
