package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.items.TestItem
import hm.binkley.layers.util.stackOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CharacterTest {
    private val character = character("TEST CHARACTER")

    @Test
    fun `should start with a character and a stat layer`() {
        character.history.map {
            it::class
        } shouldBe stackOf(
            PlayerLayer::class,
            StatLayer::class,
            InventoryLayer::class,
        )
    }

    @Test
    fun `should have a player name rule`() {
        character["PLAYER-NAME"] shouldBe ""
    }

    @Test
    fun `should have a character name rule`() {
        character["CHARACTER-NAME"] shouldBe ""
    }

    @Test
    fun `should have a net item weight`() {
        character["ITEM-WEIGHT"] shouldBe 0.0f

        val testItem = character.saveAndNext { TestItem() }

        character["ITEM-WEIGHT"] shouldBe testItem.weight
    }
}
