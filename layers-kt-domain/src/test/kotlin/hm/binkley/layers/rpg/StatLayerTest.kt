package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.rpg.Stat.DEFTNESS
import hm.binkley.layers.rpg.Stat.FORESIGHT
import hm.binkley.layers.rpg.Stat.GRIT
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.rpg.Stat.PRESENCE
import hm.binkley.layers.rpg.Stat.WIT
import hm.binkley.util.stackOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class StatLayerTest {
    private val layers = DefaultMutableLayers(
        name = "TEST LAYERS",
        defaultMutableLayer = { StatLayer() },
        initLayers = stackOf(StatLayer()),
    )

    @Test
    fun `should have might rules`() {
        layers[MIGHT.name] shouldBe 0
        layers[MIGHT.bonusKey] shouldBe -5
    }

    @Test
    fun `should have deftness rules`() {
        layers[DEFTNESS.name] shouldBe 0
        layers[DEFTNESS.bonusKey] shouldBe -5
    }

    @Test
    fun `should have grit rules`() {
        layers[GRIT.name] shouldBe 0
        layers[GRIT.bonusKey] shouldBe -5
    }

    @Test
    fun `should have wit rules`() {
        layers[WIT.name] shouldBe 0
        layers[WIT.bonusKey] shouldBe -5
    }

    @Test
    fun `should have foresight rules`() {
        layers[FORESIGHT.name] shouldBe 0
        layers[FORESIGHT.bonusKey] shouldBe -5
    }

    @Test
    fun `should have presence rules`() {
        layers[PRESENCE.name] shouldBe 0
        layers[PRESENCE.bonusKey] shouldBe -5
    }
}
