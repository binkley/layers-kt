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

    @Test
    fun `should update might directly`() = character.edit {
        MIGHT = 13
        MIGHT shouldBe 13
    }

    @Test
    fun `should update deftness directly`() = character.edit {
        DEFTNESS = 13
        DEFTNESS shouldBe 13
    }

    @Test
    fun `should update grit directly`() = character.edit {
        GRIT = 13
        GRIT shouldBe 13
    }

    @Test
    fun `should update wit directly`() = character.edit {
        WIT = 13
        WIT shouldBe 13
    }

    @Test
    fun `should update foresight directly`() = character.edit {
        FORESIGHT = 13
        FORESIGHT shouldBe 13
    }

    @Test
    fun `should update presence directly`() = character.edit {
        PRESENCE = 13
        PRESENCE shouldBe 13
    }
}
