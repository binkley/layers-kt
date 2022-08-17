package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.items.Weight.Companion.weight
import java.math.BigDecimal
import java.math.RoundingMode.HALF_EVEN

@JvmInline
value class Weight private constructor(
    /** The underlying big decimal. */
    val value: BigDecimal,
) {
    override fun toString(): String = value.toString()

    companion object {
        val ZERO = weight(BigDecimal.ZERO)

        /**
         * Creates a new [Weight] with [HALF_EVEN] rounding to 2 decimal
         * places.
         */
        fun weight(value: BigDecimal) = Weight(value.setScale(2, HALF_EVEN))
    }
}

val Int.weight get() = weight(BigDecimal(this))
val Float.weight get() = weight(BigDecimal(this.toDouble()))

operator fun Weight.unaryPlus(): Weight = this
operator fun Weight.unaryMinus(): Weight = weight(-value)
operator fun Weight.plus(other: Weight): Weight = weight(value + other.value)
operator fun Weight.minus(other: Weight): Weight = weight(value - other.value)

fun Iterable<Weight>.sum(): Weight {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += element.value
    }
    return weight(sum)
}

fun Sequence<Weight>.sum(): Weight {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += element.value
    }
    return weight(sum)
}
