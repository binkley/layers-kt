package hm.binkley.layers.dnd.items

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VolumeTest {
    @Test
    fun shouldDisplayNicelyInPounds() =
        assertEquals("1 cu.ft.", Volume.inCuft(1).toString())

    @Test
    fun shouldDisplayNicelyAsFraction() =
        assertEquals("1.1 cu.ft.", Volume.asFraction(11, 10).toString())
}
