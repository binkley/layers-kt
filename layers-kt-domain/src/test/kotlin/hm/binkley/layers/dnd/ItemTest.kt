package hm.binkley.layers.dnd

import hm.binkley.layers.Layers.LayerSurface
import hm.binkley.layers.LayersTestSupport
import hm.binkley.layers.dnd.ItemTest.EgItem
import hm.binkley.layers.dnd.Weight.Companion.WEIGHTLESS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ItemTest : LayersTestSupport<EgItem>(::EgItem) {
    @Test
    fun shouldHaveWeight() = assertEquals(WEIGHTLESS, firstLayer.weight)

    internal class EgItem(layers: LayerSurface) : Item<EgItem>(layers, "Example", WEIGHTLESS)
}
