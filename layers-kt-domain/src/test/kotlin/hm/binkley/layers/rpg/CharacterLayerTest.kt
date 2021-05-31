package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.rpg.BaseStat.DEFTNESS
import hm.binkley.layers.rpg.BaseStat.FORESIGHT
import hm.binkley.layers.rpg.BaseStat.GRIT
import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.BaseStat.PRESENCE
import hm.binkley.layers.rpg.BaseStat.WIT
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CharacterLayerTest {
    private val layers = DefaultMutableLayers<String, Any, CharacterLayer>(
        name = "TEST LAYERS",
        initLayers = listOf(CharacterLayer("TEST CHARACTER")),
        defaultMutableLayer = { CharacterLayer(it) },
    )

    @Test
    fun `should have a player name rule`() {
        layers["PLAYER-NAME"] shouldBe ""
    }

    @Test
    fun `should have a character name rule`() {
        layers["CHARACTER-NAME"] shouldBe ""
    }

    @Test
    fun `should have might rules`() {
        layers[MIGHT.name] shouldBe 8
        layers[MIGHT.bonusKey] shouldBe -1
    }

    @Test
    fun `should have deftness rules`() {
        layers[DEFTNESS.name] shouldBe 8
        layers[DEFTNESS.bonusKey] shouldBe -1
    }

    @Test
    fun `should have grit rules`() {
        layers[GRIT.name] shouldBe 8
        layers[GRIT.bonusKey] shouldBe -1
    }

    @Test
    fun `should have wit rules`() {
        layers[WIT.name] shouldBe 8
        layers[WIT.bonusKey] shouldBe -1
    }

    @Test
    fun `should have foresight rules`() {
        layers[FORESIGHT.name] shouldBe 8
        layers[FORESIGHT.bonusKey] shouldBe -1
    }

    @Test
    fun `should have presence rules`() {
        layers[PRESENCE.name] shouldBe 8
        layers[PRESENCE.bonusKey] shouldBe -1
    }
}
