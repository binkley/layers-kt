package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.items.Weight.Companion.ZERO
import hm.binkley.layers.rpg.items.Weight.Companion.weight
import java.math.BigDecimal
import java.math.RoundingMode.HALF_EVEN

/** [Weight] wraps a [BigDecimal] with 2 decimal points of precision. */
@JvmInline
value class Weight private constructor(
    /** The underlying big decimal. */
    val value: BigDecimal,
) {
    override fun toString(): String = value.toString()

    companion object {
        /** The zero weight. */
        val ZERO: Weight = weight(BigDecimal.ZERO)

        /**
         * Creates a [Weight] with [HALF_EVEN] rounding to 2 decimal places.
         */
        fun weight(value: BigDecimal): Weight =
            Weight(value.setScale(2, HALF_EVEN))
    }
}

/** Creates a [Weight] from an [Int]. */
val Int.weight: Weight get() = weight(BigDecimal(this))

/** Creates a [Weight] from a [Float]. */
val Float.weight: Weight get() = weight(BigDecimal(this.toDouble()))

/** Unary plus is the same as `this` weight. */
operator fun Weight.unaryPlus(): Weight = this

/** Negates the value of `this` weight. */
operator fun Weight.unaryMinus(): Weight = weight(-value)

/** Adds `this` weight to [other] weight. */
operator fun Weight.plus(other: Weight): Weight = weight(value + other.value)

/** Subtracts [other] weight from `this` weight. */
operator fun Weight.minus(other: Weight): Weight = weight(value - other.value)

/** Sums weights over an iteration. */
fun Iterable<Weight>.sum(): Weight {
    var sum = ZERO
    for (element in this) {
        sum += element
    }
    return sum
}

/** Sums weights over a sequence. (Note: sequences may never complete.) */
fun Sequence<Weight>.sum(): Weight {
    var sum = ZERO
    for (element in this) {
        sum += element
    }
    return sum
}
