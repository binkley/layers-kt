package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.items.Backpack.Companion.backpack
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class BackpackTest {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should add and remove an item`() {
        var backpack = character.commitAndNext { backpack<Item<*>>() }
        val item = character.commitAndNext { TestItem() }

        backpack = character.commitAndNext { backpack.stow(item) }
        backpack.contents shouldBe listOf(item)

        backpack = character.commitAndNext { backpack.unstow(item) }
        backpack.contents shouldBe emptyList()

        character.history.size shouldBe 6
    }
}
