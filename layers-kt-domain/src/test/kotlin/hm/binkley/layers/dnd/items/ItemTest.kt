package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layers.LayerSurface
import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.dnd.items.ItemTest.EgItem
import hm.binkley.layers.dnd.items.Volume.Companion.SPACELESS
import hm.binkley.layers.dnd.items.Weight.Companion.WEIGHTLESS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ItemTest : LayersTestSupport<EgItem>(::EgItem) {
    @Test
    fun shouldHaveWeight() = assertEquals(WEIGHTLESS, firstLayer.weight)

    @Test
    fun shouldHaveVolume() = assertEquals(SPACELESS, firstLayer.volume)

    internal class EgItem(layers: LayerSurface) : Item<EgItem>(layers,
            "Example", WEIGHTLESS, SPACELESS)
}
