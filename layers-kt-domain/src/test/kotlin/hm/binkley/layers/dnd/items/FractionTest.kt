package hm.binkley.layers.dnd.items

import org.junit.jupiter.api.Assertions.assertEquals

internal class FractionTest {
    @org.junit.jupiter.api.Test
    fun shouldDiplayNicely()
            = assertEquals("0.7", EgFraction(2, 3).toString())

    @org.junit.jupiter.api.Test
    fun shouldDiplayWithoutFractions()
            = assertEquals("1", EgFraction(1, 1).toString())

    @org.junit.jupiter.api.Test
    fun shouldAdd()
            = assertEquals(EgFraction(3, 2), EgFraction(5, 6).
            add(EgFraction(2, 3)))

    internal class EgFraction(numerator: Int, denominator: Int)
        : Fraction<EgFraction>(::EgFraction, numerator, denominator)
}
