package hm.binkley.layers.dnd

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FractionTest {
    @Test
    fun shouldDiplayNicely()
            = assertEquals("0.7", EgFraction(2, 3).toString())

    @Test
    fun shouldDiplayWithoutFractions()
            = assertEquals("1", EgFraction(1, 1).toString())

    @Test
    fun shouldAdd()
            = assertEquals(EgFraction(3, 2), EgFraction(5, 6).
            add(EgFraction(2, 3)))

    internal class EgFraction(numerator: Int, denominator: Int)
        : Fraction<EgFraction>(::EgFraction, numerator, denominator)
}
