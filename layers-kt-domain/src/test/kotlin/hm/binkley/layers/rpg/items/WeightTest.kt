package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.items.Weight.Companion.weight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class WeightTest {
    @Test
    fun `should have a zero`() {
        Weight.ZERO shouldBe weight(BigDecimal.ZERO)
        0.weight shouldBe weight(BigDecimal.ZERO)
        0.0f.weight shouldBe weight(BigDecimal.ZERO)
    }

    @Test
    fun `should round`() {
        1.234f.weight shouldBe weight(BigDecimal.valueOf(1.23f.toDouble()))
    }

    @Test
    fun `should have 2 decimal places`() {
        1.weight.value.scale() shouldBe 2
    }

    @Test
    fun `should leave weights alone`() {
        +(1.weight) shouldBe 1.weight
    }

    @Test
    fun `should negate weights`() {
        -(1.weight) shouldBe (-1).weight
    }

    @Test
    fun `should add weights`() {
        1.weight + 2.weight shouldBe 3.weight
    }

    @Test
    fun `should subtract weights`() {
        3.weight - 2.weight shouldBe 1.weight
    }

    @Test
    fun `should sum weights`() {
        listOf(1.weight, 2.weight, 3.weight).sum() shouldBe 6.weight
    }
}
