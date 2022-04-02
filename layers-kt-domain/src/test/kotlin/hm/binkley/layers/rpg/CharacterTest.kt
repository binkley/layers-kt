package hm.binkley.layers.rpg

import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.items.TestItem
import hm.binkley.util.stackOf
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
            // TODO: Details layer: physical, personality, etc
            InventoryLayer::class,
        )
    }

    @Test
    fun `should have a player name rule`() {
        character["PLAYER_NAME"] shouldBe ""
    }

    @Test
    fun `should have a character name rule`() {
        character["CHARACTER_NAME"] shouldBe ""
    }

    @Test
    fun `should have a net item weight`() {
        character["ITEM-WEIGHT"] shouldBe 0.0f

        val testItem = character.saveAndNext { TestItem() }

        character["ITEM-WEIGHT"] shouldBe testItem.weight
    }

    @Test
    fun `should update player name directly`(): Unit = character.edit {
        PLAYER_NAME = "Bob"
        PLAYER_NAME shouldBe "Bob"
    }

    @Test
    fun `should update character name directly`(): Unit = character.edit {
        CHARACTER_NAME = "Bob"
        CHARACTER_NAME shouldBe "Bob"
    }

    @Test
    fun `should update might directly`(): Unit = character.edit {
        MIGHT = 13
        ++MIGHT
        MIGHT shouldBe 14
    }

    @Test
    fun `should update deftness directly`(): Unit = character.edit {
        DEFTNESS = 13
        ++DEFTNESS
        DEFTNESS shouldBe 14
    }

    @Test
    fun `should update grit directly`(): Unit = character.edit {
        GRIT = 13
        ++GRIT
        GRIT shouldBe 14
    }

    @Test
    fun `should update wit directly`(): Unit = character.edit {
        WIT = 13
        ++WIT
        WIT shouldBe 14
    }

    @Test
    fun `should update foresight directly`(): Unit = character.edit {
        FORESIGHT = 13
        ++FORESIGHT
        FORESIGHT shouldBe 14
    }

    @Test
    fun `should update presence directly`(): Unit = character.edit {
        PRESENCE = 13
        ++PRESENCE
        PRESENCE shouldBe 14
    }

    @Test
    fun `should update item weight directly`(): Unit = character.edit {
        `ITEM-WEIGHT` = 1.1f
        ++`ITEM-WEIGHT`
        `ITEM-WEIGHT` shouldBe 2.1f
    }
}
