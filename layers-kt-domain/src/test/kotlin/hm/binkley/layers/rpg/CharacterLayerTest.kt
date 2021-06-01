package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.util.stackOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CharacterLayerTest {
    private val layers = DefaultMutableLayers(
        name = "TEST LAYERS",
        initLayers = stackOf(CharacterLayer()),
        defaultMutableLayer = { CharacterLayer() },
    )

    @Test
    fun `should have a player name rule`() {
        layers["PLAYER-NAME"] shouldBe ""
    }

    @Test
    fun `should have a character name rule`() {
        layers["CHARACTER-NAME"] shouldBe ""
    }
}
